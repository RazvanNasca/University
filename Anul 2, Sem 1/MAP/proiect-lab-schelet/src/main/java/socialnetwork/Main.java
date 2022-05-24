package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.*;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.CerereDBRepository;
import socialnetwork.repository.database.MesajeDBRepository;
import socialnetwork.repository.database.PrietenieDBRepository;
import socialnetwork.repository.database.UtilizatorDBRepository;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.CerereService;
import socialnetwork.service.MesajeService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.UtilizatorService;

import java.security.Provider;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    private static void Meniu()
    {
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Adauga prieten");
        System.out.println("4. Sterge prieten");
        System.out.println("5. Numar de comunitati");
        System.out.println("6. Cea mai sociabila comunitate");
        System.out.println("7. Informatii utilizatori");
        System.out.println("8. Prieteniile unui utilizator");
        System.out.println("9. Prieteniile unui utilizator dintr-o luna anume");
        System.out.println("10. Actiuni pentru un anumit utilizator\n");
    }

    private static void MeniuUtilizator()
    {
        System.out.println("1. Trimite cerere de prietenie");
        System.out.println("2. Raspunde unei cereri de prietenie");
        System.out.println("3. Vezi cererile in asteptare");
        System.out.println("4. Trimite mesaje");
        System.out.println("5. Raspunde la un mesaj");
        System.out.println("6. Afiseaza-mi mesajele");
        System.out.println("7. Afiseaza-mi conversatia cu cineva");
        System.out.println("0. Exit\n");

    }


    public static void main(String[] args)
    {
        String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileNamePrietenii=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.prietenii");
        final String url = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        final String username= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        final String password= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");

        Repository<Tuple<Long, Long>, Prietenie> prietenieFileRepository = new PrietenieFile(fileNamePrietenii, new PrietenieValidator());
        Repository<Tuple<Long, Long>, Prietenie> prietenieDBRepository = new PrietenieDBRepository(url, username, password, new PrietenieValidator());
        Repository<Tuple<Long, Long>, CererePrietenie> cerereDBRepository = new CerereDBRepository(url, username, password, new CerereValidator());
        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName, new UtilizatorValidator());
        Repository<Long, Utilizator> userDBRepository = new UtilizatorDBRepository(url, username, password, new UtilizatorValidator());
        Repository<Long, Mesaje> mesajeDBRepository = new MesajeDBRepository(url, username, password, new MesajeValidator());

        UtilizatorService utilizatorService = new UtilizatorService(userDBRepository, prietenieDBRepository);
        PrietenieService prietenieService = new PrietenieService(prietenieDBRepository, userDBRepository);
        CerereService cerereService = new CerereService(cerereDBRepository,prietenieDBRepository, userDBRepository);
        MesajeService mesajeService = new MesajeService(mesajeDBRepository,prietenieDBRepository, userDBRepository);


        label:
        while(true)
        {
            Meniu();
            String cmd = "";
            Scanner scan = new Scanner(System.in);

            System.out.println("Introduceti comanda: ");

            cmd = scan.next();

            switch (cmd) {
                case "1": {
                    /// Adauga utilizator
                    String id, nume, prenume;

                    System.out.println("Introduceti id: ");
                    id = scan.next();

                    if(id.matches(".*\\D+.*"))
                    {
                        System.out.println("Invalid ID!");
                        break;
                    }

                    System.out.println("Introduceti numele: ");
                    nume = scan.next();
                    System.out.println("Introduceti prenumele: ");
                    prenume = scan.next();


                    try {
                        Utilizator utilizator = new Utilizator(nume, prenume);
                        utilizator.setId(Long.parseLong(id));
                        utilizatorService.addUtilizator(utilizator);
                        System.out.println("Utilizator adaugat!\n");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.toString());
                    }

                    break;
                }
                case "2": {
                    /// Sterge utilizator
                    System.out.println("Introduceti id: ");
                    String id;
                    id = scan.next();

                    if(id.matches(".*\\D+.*"))
                    {
                        System.out.println("Invalid ID!");
                        break;
                    }


                    try {
                        utilizatorService.deleteUtilizator(Long.parseLong(id));
                        System.out.println("Utilizator sters!\n");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "3": {
                    /// Adauga prietenie
                    String id1, id2;

                    System.out.println("Introduceti id 1: ");
                    id1 = scan.next();
                    System.out.println("Introduceti id 2: ");
                    id2 = scan.next();

                    if(id1.matches(".*\\D+.*") || id2.matches(".*\\D+.*"))
                    {
                        System.out.println("Invalid ID!");
                        break;
                    }

                    Prietenie prietenie = new Prietenie();
                    prietenie.setId(new Tuple(Long.parseLong(id1), Long.parseLong(id2)));
                    prietenie.setDate(LocalDateTime.now());

                    try {
                        prietenieService.addPrietenie(prietenie);
                        System.out.println("Prietenie formata!\n");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.toString());
                    }

                    break;
                }
                case "4": {
                    /// Sterge prietenie
                    String id1, id2;

                    System.out.println("Introduceti id 1: ");
                    id1 = scan.next();
                    System.out.println("Introduceti id 2: ");
                    id2 = scan.next();

                    if(id1.matches(".*\\D+.*") || id2.matches(".*\\D+.*"))
                    {
                        System.out.println("Invalid ID!");
                        break;
                    }


                    Tuple t = new Tuple(Long.parseLong(id1), Long.parseLong(id2));

                    try {
                        prietenieService.deletePrietenie(t);
                        System.out.println("Prietenie stearsa!\n");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    } catch (RepoException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "5":
                    /// numarul de componente conexe
                    int nrComponente = prietenieService.NumarComponnteConexe();
                    System.out.println("Numarul de comunitati este: " + nrComponente + "\n");
                    break;
                case "6":
                    /// comunitatea ce mai mare;
                    StringBuilder rez = prietenieService.Comunitaate();

                    System.out.println(rez + "\n");
                    break;
                case "7":
                    /// afiseaza lista de utilizatori
                    System.out.println("Lista cu toti utilizatorii:");
                    for (Utilizator it : utilizatorService.getAll())
                        System.out.println(it.toString());
                    System.out.println("\n");
                    break;
                case "8":
                    /// afiseaza toate prieteniile unui utilizator
                    System.out.println("Introduceti un id de utilizator!");
                    String id;
                    id = scan.next();
                    Map<Utilizator, LocalDateTime> all = utilizatorService.ceva(Long.parseLong(id));

                    for(Map.Entry<Utilizator, LocalDateTime> fr : all.entrySet())
                        System.out.println("Numele: " + fr.getKey().getFirstName() + " Prenumele: " + fr.getKey().getLastName() + " Data: " + fr.getValue());
                    break;

                case "9":
                    /// afiseaza prieteniile dintr-o anumita luna
                    System.out.println("Introduceti un id de utilizator!");
                    String id1;
                    id1 = scan.next();
                    System.out.println("Introduceti o luna!");
                    String luna;
                    luna = scan.next();

                    Map<Utilizator, LocalDateTime> prieteni = utilizatorService.ceva(Long.parseLong(id1));

                    for(Map.Entry<Utilizator, LocalDateTime> fr : prieteni.entrySet())
                    {
                        LocalDateTime lunaFr = fr.getValue();
                        String lunaStr = lunaFr.toString();
                        String[] arg = lunaStr.split("-");

                        if(arg[1].equals(luna))
                            System.out.println("Numele: " + fr.getKey().getFirstName() + " Prenumele: " + fr.getKey().getLastName() + " Data: " + fr.getValue());
                    }
                    break;

                case "10":
                    /// meniul de logare
                    System.out.println("Introduceti id-ul numelui tau:");

                    String idMain;
                    idMain = scan.next();

                    if(idMain.matches(".*\\D+.*"))
                    {
                        System.out.println("Invalid ID!");
                        break;
                    }

                    boolean gasit = false;
                    for(Utilizator it: utilizatorService.getAll())
                        if(it.getId() == Long.parseLong(idMain))
                        {
                            gasit = true;
                            break;
                        }

                    if(!gasit)
                    {
                        System.out.println("Utilizatorul nu exista!");
                        break;
                    }

                    while(true)
                    {
                        MeniuUtilizator();
                        String cmdUtilizator = "";
                        Scanner scan1 = new Scanner(System.in).useDelimiter("\n");

                        System.out.println("Introduceti comanda: ");

                        cmdUtilizator = scan1.next();

                        if(cmdUtilizator.equals("1"))
                        {
                            /// Adauga prietenie
                            String idPrieten;
                            System.out.println("Introduceti id prieten: ");
                            idPrieten = scan1.next();

                            if(idPrieten.matches(".*\\D+.*"))
                            {
                                System.out.println("Invalid ID!");
                                break;
                            }

                            CererePrietenie cerere = new CererePrietenie();
                            cerere.setId(new Tuple(Long.parseLong(idMain), Long.parseLong(idPrieten)));
                            cerere.setDate(LocalDateTime.now());
                            cerere.setStatus("PENDING");

                            try {
                                cerereService.addCerere(cerere);
                                System.out.println("Cerere trimisa!\n");
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            } catch (RepoException e) {
                                System.out.println(e.getMessage());
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.toString());
                            }
                        }
                        else
                            if(cmdUtilizator.equals("2"))
                            {
                                /// raspunde unei cereri in asteptare
                                System.out.println("Alegere utilizatorul la care raspunzi cererii de prietenie in asteptare");
                                String idPrieten;
                                idPrieten = scan1.next();

                                String raspuns;
                                System.out.println("Introduceti un raspuns (A - approved sau R - rejected)");
                                raspuns = scan1.next();


                                if(idPrieten.matches(".*\\D+.*"))
                                {
                                    System.out.println("Invalid ID!");
                                    break;
                                }

                                if(!raspuns.equals("A") && !raspuns.equals("R"))
                                {
                                    System.out.println("Raspuns invalid!");
                                    break;
                                }

                                try {
                                    cerereService.raspundeCerere(new Tuple(Long.parseLong(idMain),Long.parseLong(idPrieten)), raspuns);
                                    System.out.println("Cerere actualizata!\n");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                } catch (RepoException e) {
                                    System.out.println(e.getMessage());
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.toString());
                                }

                            }
                            else
                                if(cmdUtilizator.equals("3"))
                                {
                                    /// vezi cererile in asteptare
                                    List<Utilizator> cereriAsteapta = cerereService.CereriInAsteptare(Long.parseLong(idMain));
                                    for(Utilizator it: cereriAsteapta)
                                        System.out.println(it.getId());
                                }
                                else
                                    if(cmdUtilizator.equals("4"))
                                    {
                                        /// trimite mesaje
                                        Long idMesaj = 0L;
                                        for(Mesaje it : mesajeService.getAll())
                                            idMesaj++;

                                        idMesaj++;

                                        System.out.println("Introduceti mesaju:");
                                        String continut;
                                        continut = scan1.next();

                                        System.out.println("Introduceti id-urile prietenilor care o sa primeasca mesajul:");
                                        String destinatie;
                                        destinatie = scan1.next();

                                        List<Long> to = mesajeService.Parseaza(destinatie);

                                        Mesaje mesaj = new Mesaje(Long.parseLong(idMain), continut, LocalDateTime.now());

                                        for(Long it : to)
                                            mesaj.getTo().add(it);

                                        try{
                                            mesaj.setId(idMesaj);
                                            mesajeService.addMesaj(mesaj);
                                            System.out.println("Mesajul a fost trimis cu succes!");
                                        }
                                        catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        } catch (RepoException e) {
                                            System.out.println(e.getMessage());
                                        } catch (IllegalArgumentException e) {
                                            System.out.println(e.toString());
                                        }

                                    }
                                    else
                                        if(cmdUtilizator.equals("5"))
                                        {
                                            /// reply
                                            System.out.println("Introduceti id-ul mesajului la care o sa raspundeti:");
                                            String idMsg = scan1.next();

                                            Long idMesaj = 0L;
                                            for(Mesaje it : mesajeService.getAll())
                                                idMesaj++;

                                            idMesaj++;

                                            System.out.println("Introduceti mesaju:");
                                            String continut;
                                            continut = scan1.next();
                                            Mesaje mesaj = new Mesaje(Long.parseLong(idMain), continut, LocalDateTime.now());

                                            System.out.println("1. Reply doar la sursa mesajului");
                                            System.out.println("2. Reply la toti\n");
                                            String cati = scan1.next();

                                            Mesaje mesajulInitial = mesajeService.findOne(Long.parseLong(idMsg));
                                            mesaj.getTo().add(mesajulInitial.getFrom());

                                            if(cati.equals("2"))
                                                for(Long it : mesajulInitial.getTo())
                                                    if(it != Long.parseLong(idMain))
                                                        mesaj.getTo().add(it);

                                            try{
                                                mesaj.setId(idMesaj);
                                                mesajeService.addMesaj(mesaj);
                                                mesajeService.addReply(Long.parseLong(idMain), mesajulInitial);
                                                System.out.println("Mesajul a fost trimis cu succes!");
                                            }
                                            catch (ValidationException e) {
                                                System.out.println(e.getMessage());
                                            } catch (RepoException e) {
                                                System.out.println(e.getMessage());
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.toString());
                                            }
                                        }
                                        else
                                            if(cmdUtilizator.equals("6"))
                                            {
                                                /// afisare mesaje
                                                List<Mesaje> mesajele = mesajeService.mesajeleUnuiUtilizator(Long.parseLong(idMain));
                                                for(Mesaje it : mesajele)
                                                    System.out.println("Id mesaj " + it.getId() + ": " + it.getMesaj());
                                            }
                                            else
                                            if(cmdUtilizator.equals("7"))
                                            {
                                                System.out.println("Introduceti id-ul userului cu care doresti conversatiile");
                                                String idPrieten = scan1.next();
                                                List<Mesaje> conv = mesajeService.conversatie(Long.parseLong(idMain), Long.parseLong(idPrieten));

                                                conv.stream()
                                                        .sorted((x,y) -> x.getData().compareTo(y.getData()))
                                                        .forEach(x -> System.out.println("Mesajul: " + x.getMesaj() + " din data de " + x.getData()));
                                            }
                                            else
                                            {
                                                System.out.println("Meniu principal!");
                                                break;
                                            }
                    }

                    break;

                default:
                    System.out.println("Sfarsitul programului!\n");
                    break label;
            }
        }
    }

}


