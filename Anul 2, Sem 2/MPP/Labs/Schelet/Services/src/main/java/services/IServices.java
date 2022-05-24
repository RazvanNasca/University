package services;

import model.AppUser;

public interface IServices {

    public AppUser Login(AppUser user, IObserver client) throws MyException;

    public void logout(AppUser user, IObserver client) throws MyException;

    AppUser[] getLoggedUsers() throws MyException;

}
