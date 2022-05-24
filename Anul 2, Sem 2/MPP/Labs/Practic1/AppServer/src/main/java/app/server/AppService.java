package app.server;

import app.model.*;
import app.persistence.interfaces.*;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;
import javassist.compiler.ast.Pair;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class AppService implements IAppServices {
    private UserRepository userRepository;
    private CuvantRepository cuvantRepository;
    private RundaRepository rundaRepository;
    private ManaRepository manaRepository;
    private JocRepository jocRepository;

    private Map<User, IAppObserver> loggedClients;

    private List<User> readyUsers;

    private int nrRunda;
    private Runda runda;
    private Joc joc;

    private Map<User, String> cuvinteInitiale;
    private Map<User, Map<User, String>> litereTrimise;


    public AppService(UserRepository userRepository, CuvantRepository cuvantRepository, JocRepository jocRepository, RundaRepository rundaRepository, ManaRepository manaRepository) {
        this.userRepository = userRepository;
        this.cuvantRepository = cuvantRepository;
        this.jocRepository = jocRepository;
        this.rundaRepository = rundaRepository;
        this.manaRepository = manaRepository;

        loggedClients = new ConcurrentHashMap<>();
        readyUsers = new ArrayList<>();
        cuvinteInitiale = new ConcurrentHashMap<>();
        litereTrimise = new ConcurrentHashMap<>();
    }

    public synchronized User login(String username, String password, IAppObserver client) throws AppException {

        if(username.equals("") || password.equals(""))
            throw  new AppException("You have to complete all fields!");
        User user = userRepository.getUser(username, password);
        if (user == null)
            throw new AppException("There is no user with this username and password");
        else {
            if (loggedClients.get(user) != null)
                throw new AppException("User already logged in!");
            loggedClients.put(user, client);
        }
        return user;
    }

    public synchronized void logout(User user){
        loggedClients.remove(user);
    }

    public synchronized void startJoc(User user, String cuvant) throws AppException {

        readyUsers.add(user);
        cuvinteInitiale.put(user, cuvant);
        if(readyUsers.size() == 3)
        {
            nrRunda = 0;
            Map<User, Cuvant> prop = new HashMap<>();
            for(User u : readyUsers){
                Cuvant cuv = new Cuvant(cuvant, u.getUsername());
                cuv = cuvantRepository.save(cuv);
                prop.put(u, cuv);
            }
            joc = jocRepository.save(new Joc(prop));
            newRound();
            notifyPlayers();
        }
    }

    public synchronized void newRound(){

        nrRunda++;
        litereTrimise.clear();
        runda = rundaRepository.save(new Runda(joc));
    }

    @Override
    public synchronized void trimiteMana(User user, String jucatorUsername, String litera) throws AppException {

        User jucator = userRepository.findOne(jucatorUsername);
        litereTrimise.put(user, new HashMap<>(){{
            put(jucator, litera);
        }});
        if(litereTrimise.size() == 3){
            sfRunda();
        }
    }

    private synchronized void sfRunda(){

        List<ManaDTO> pozitii = new ArrayList<>();
        for(User u : litereTrimise.keySet()){
            int punctaj = 0;
            Map<User, String> alegeri = litereTrimise.get(u);
            User jucator = new ArrayList<>(alegeri.keySet()).get(0);

            for(int i = 0; i < cuvinteInitiale.get(jucator).length(); i++)
                    if( cuvinteInitiale.get(jucator).charAt(i) == alegeri.get(jucator).charAt(0))
                punctaj += 1;

            ManaId manaId = new ManaId();
            manaId.setUser(u);
            manaId.setRunda(runda);

            Mana mana = new Mana(alegeri.get(jucator), jucator, punctaj);
            mana.setPk(manaId);
            manaRepository.save(mana);

            ManaDTO manaDTO = new ManaDTO(jucator.getUsername(), alegeri.get(jucator), punctaj);
            pozitii.add(manaDTO);
        }

        notifySfRunda(pozitii);
        if(nrRunda<3)
            newRound();
        else
            sfJoc();
    }

    private void sfJoc(){
        List<ManaDTO> clasament = new ArrayList<>();
        for(User u : readyUsers){
            List<ManaDTO> maini = manaRepository.getMainiByJucatorSiJoc(joc.getId(), u.getUsername());
            ManaDTO mana = new ManaDTO(u.getUsername(),  maini.stream().map(ManaDTO::getPunctaj).reduce(0, Integer::sum));
            clasament.add(mana);
        }
        clasament = clasament.stream().sorted(Comparator.comparingInt(ManaDTO::getPunctaj).reversed()).collect(Collectors.toList());
        notifyClasament(clasament);

        nrRunda = 0;
        litereTrimise.clear();
        cuvinteInitiale.clear();
        readyUsers.clear();
    }

    private final int defaultThreadsNo = 5;

    private void notifyPlayers() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us : loggedClients.keySet()){
            IAppObserver appClient = loggedClients.get(us);
            List<ManaDTO> users = new ArrayList<>();
            for(User u : loggedClients.keySet()){
                if(!us.equals(u))
                {
                    String intialString = "";
                    int nrLitere = cuvinteInitiale.get(u).length();
                    while(nrLitere != 0)
                    {
                        intialString += "_";
                        nrLitere--;
                    }
                    users.add(new ManaDTO(u.getUsername(), intialString));
                }

            }
            if (appClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getUsername()+ "] usernames ["+users+"] logged in.");
                        appClient.jocNou(users);
                    } catch (RemoteException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }

    private void notifySfRunda(List<ManaDTO> pozitii){

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us : loggedClients.keySet()){
            IAppObserver appClient = loggedClients.get(us);
            if (appClient != null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getUsername()+ "]");
                        appClient.sfRunda(pozitii);
                    } catch (RemoteException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }

    private void notifyClasament(List<ManaDTO> clasament){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us : loggedClients.keySet()){
            IAppObserver appClient = loggedClients.get(us);
            if (appClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getUsername()+ "]");
                        appClient.clasament(clasament);
                    } catch (RemoteException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }
}