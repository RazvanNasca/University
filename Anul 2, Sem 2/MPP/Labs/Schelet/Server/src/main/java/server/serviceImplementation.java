package server;

import model.AppUser;
import repository.RepoUser;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import services.*;

public class serviceImplementation implements IServices {

    private RepoUser repoUser;
    private Map<String, IObserver> loggedUsers;

    public serviceImplementation(RepoUser repoUser){
        this.repoUser = repoUser;
        loggedUsers = new ConcurrentHashMap<>();
    }


    public synchronized AppUser Login(AppUser user, IObserver client) throws MyException
    {
        try{
            AppUser user1 = repoUser.findOne(user.getName());

            if(!user.getPassword().equals(user1.getPassword()))
                throw new MyException("Wrong password!");

            if(loggedUsers.get(user.getName())!=null)
                throw new MyException("User already logged in.");
            loggedUsers.put(user.getName(), client);
            notifyUsersLoggedIn(user);
            return user;
        }
        catch(Exception e)
        {
            throw new MyException("Repo error" + e);
        }
    }

    private final int defaultThreadsNo = 5;
    private void notifyUsersLoggedIn(AppUser user) throws MyException {
        Iterable<AppUser> users = repoUser.findAll();
        System.out.println("Logged " + users);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(AppUser us : users){
            IObserver festivalClient = loggedUsers.get(us.getName());
            if (festivalClient != null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getName()+ "] user ["+user.getName()+"] logged in.");
                        festivalClient.userLoggedIn(user);
                    } catch (MyException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }

    public synchronized void logout(AppUser user, IObserver client) throws MyException {
        IObserver localClient = loggedUsers.remove(user.getName());
        if (localClient==null)
            throw new MyException("User "+user.getName()+" is not logged in.");
        notifyUsersLoggedOut(user);
    }

    private void notifyUsersLoggedOut(AppUser user) throws MyException {
        Iterable<AppUser> users = repoUser.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        for(AppUser us : users){
            IObserver festivalClient = loggedUsers.get(us.getName());
            if (festivalClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+us.getName()+"] user ["+user.getName()+"] logged out.");
                        festivalClient.userLoggedOut(user);
                    } catch (MyException e) {
                        System.out.println("Error notifying friend " + e);
                    }
                });

        }
        executor.shutdown();
    }


    public synchronized AppUser[] getLoggedUsers() throws MyException {
        Iterable<AppUser> users = repoUser.findAll();
        Set<AppUser> result = new TreeSet<>();
        System.out.println("Logged users");
        for (AppUser user : users){
            if (loggedUsers.containsKey(user.getName())){
                result.add(user);
                System.out.println("+" + user.getName());
            }
        }
        System.out.println("Size " + result.size());
        return result.toArray(new AppUser[result.size() - 1]);
    }

}
