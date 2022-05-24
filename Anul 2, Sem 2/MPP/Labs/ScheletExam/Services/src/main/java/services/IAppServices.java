package services;

import model.User;

public interface IAppServices
{
    User userLogin(String email , String password) throws ServiceException;
    void userLogout(User user) throws ServiceException;
}
