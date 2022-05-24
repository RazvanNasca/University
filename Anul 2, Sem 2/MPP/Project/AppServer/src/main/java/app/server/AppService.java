package app.server;

import app.model.*;
import app.persistence.interfaces.*;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class AppService implements IAppServices
{
    private IUserRepository userRepository;

    private Map<User, IAppObserver> loggedClients;

    private List<User> readyUsers;

    private Map<User, List<Character>> initialChars;
    private Map<User, Map<User, Character>> sentChars;


    public AppService(IUserRepository userRepository)
    {
        this.userRepository = userRepository;

        loggedClients = new ConcurrentHashMap<>();
        readyUsers = new ArrayList<>();
        initialChars = new ConcurrentHashMap<>();
        sentChars = new ConcurrentHashMap<>();
    }

    public synchronized User login(String username, String password, IAppObserver client) throws AppException
    {
        if(username.equals("") || password.equals(""))
            throw  new AppException("You have to complete all fields!");
        User user = userRepository.getUser(username, password);
        if (user == null)
            throw new AppException("User not found!");
        else {
            if (loggedClients.get(user) != null)
                throw new AppException("User already logged in!");
            loggedClients.put(user, client);
        }
        return user;
    }

    public synchronized void logout(User user)
    {
        loggedClients.remove(user);
    }

    public synchronized void startGame(User user, String word) throws AppException
    {
        readyUsers.add(user);
        List<Character> literePropuse = new ArrayList<>();
        for(int i = 0; i < word.length(); i++)
            literePropuse.add(word.charAt(i));
        initialChars.put(user, literePropuse);
    }

}