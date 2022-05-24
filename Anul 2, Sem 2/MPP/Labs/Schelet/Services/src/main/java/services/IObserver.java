package services;

import model.AppUser;

public interface IObserver {

    void userLoggedIn(AppUser user) throws MyException;

    void userLoggedOut(AppUser user) throws MyException;

}
