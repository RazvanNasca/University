package server.service;

import model.User;
import persistence.IUserRepository;
import services.IAppServices;
import services.ServiceException;

import java.util.HashSet;
import java.util.Set;

public class Service implements IAppServices
{
    Set<User> loggedUsers = new HashSet<>();
    IUserRepository userRepository;

    public Service()
    {

    }

    public Service(IUserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User userLogin(String email, String password) throws ServiceException
    {
        User u = userRepository.findOne(email);
        if (u == null)
            throw new ServiceException("User not found");
        if (u.getPassword().equals(password))
        {
            if (loggedUsers.contains(u))
                throw new ServiceException("User already logged in");
            loggedUsers.add(u);
        }
        return u;
    }

    @Override
    public void userLogout(User user) throws ServiceException
    {
        loggedUsers.remove(user);
    }
}
