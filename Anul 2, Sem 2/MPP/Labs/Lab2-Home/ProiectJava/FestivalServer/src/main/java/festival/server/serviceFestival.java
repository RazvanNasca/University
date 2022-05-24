package festival.server;

import festival.model.Buyer;
import festival.model.Show;
import festival.model.User;
import festival.services.FestivalException;
import festival.services.IFestivalObserver;
import festival.services.IFestivalServices;
import repository.RepoBuyer;
import repository.RepoShow;
import repository.RepoUser;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class serviceFestival implements IFestivalServices {

    private RepoBuyer repoBuyer;
    private RepoShow repoShow;
    private RepoUser repoUser;
    private Map<String, IFestivalObserver> loggedUsers;

    public serviceFestival(RepoBuyer repoBuyer, RepoShow repoShow, RepoUser repoUser){
        this.repoBuyer = repoBuyer;
        this.repoUser = repoUser;
        this.repoShow = repoShow;
        loggedUsers = new ConcurrentHashMap<>();
    }

    public List<Show> getAllShows()
    {
        return repoShow.findAll();
    }

    public List<Buyer> getAllBuyers()
    {
        return repoBuyer.findAll();
    }

    public synchronized User Login(User user, IFestivalObserver client) throws FestivalException
    {
        try{
            User user1 = repoUser.findOne(user.getId());

            if(!user.getPassword().equals(user1.getPassword()))
                throw new FestivalException("Wrong password!");

            if(loggedUsers.get(user.getId())!=null)
                throw new FestivalException("User already logged in.");
            loggedUsers.put(user.getId(), client);
            notifyUsersLoggedIn(user);
            return user;
        }
        catch(Exception e)
        {
            throw new FestivalException("Repo error" + e);
        }
    }

    private final int defaultThreadsNo = 5;
    private void notifyUsersLoggedIn(User user) throws FestivalException {
        Iterable<User> users = repoUser.findAll();
        System.out.println("Logged " + users);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us : users){
            IFestivalObserver festivalClient = loggedUsers.get(us.getId());
            if (festivalClient != null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getId()+ "] user ["+user.getId()+"] logged in.");
                        festivalClient.userLoggedIn(user);
                    } catch (FestivalException | RemoteException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }

    public synchronized void logout(User user, IFestivalObserver client) throws FestivalException {
        IFestivalObserver localClient = loggedUsers.remove(user.getId());
        if (localClient==null)
            throw new FestivalException("User "+user.getId()+" is not logged in.");
        notifyUsersLoggedOut(user);
    }

    private void notifyUsersLoggedOut(User user) throws FestivalException {
        Iterable<User> users = repoUser.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        for(User us : users){
            IFestivalObserver festivalClient = loggedUsers.get(us.getId());
            if (festivalClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+us.getId()+"] user ["+user.getId()+"] logged out.");
                        festivalClient.userLoggedOut(user);
                    } catch (FestivalException | RemoteException e) {
                        System.out.println("Error notifying friend " + e);
                    }
                });

        }
        executor.shutdown();
    }

    public List<Show> searchArtistByDate(LocalDateTime date) throws FestivalException
    {
        try{
            return repoShow.searchArtistByDate(date);
        }
        catch(Exception e)
        {
            throw new FestivalException("Repo error" + e);
        }
    }

    public Buyer saveBuyer(String name, Integer noTickets, Show Show) throws FestivalException
    {
        try{
            Buyer buyer = new Buyer(name, noTickets, Show);
            buyer.setId(getAllBuyers().size() + 1);
            repoBuyer.save(buyer);
            Show.setRemainingTickets(Show.getRemainingTickets() - noTickets);

            /// notify all logged users
            for(Map.Entry<String,IFestivalObserver> receiverClient : loggedUsers.entrySet())
                receiverClient.getValue().showUpdate(Show);

            return buyer;
        }
        catch(Exception e)
        {
            throw new FestivalException("Repo error" + e);
        }
    }

    public synchronized User[] getLoggedUsers() throws FestivalException {
        Iterable<User> users = repoUser.findAll();
        Set<User> result = new TreeSet<>();
        System.out.println("Logged users");
        for (User user : users){
            if (loggedUsers.containsKey(user.getId())){
                result.add(user);
                System.out.println("+" + user.getId());
            }
        }
        System.out.println("Size " + result.size());
        return result.toArray(new User[result.size() - 1]);
    }

}
