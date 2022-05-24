package festival.services;

import festival.model.Show;
import festival.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFestivalObserver extends Remote {

    void showUpdate(Show show) throws FestivalException, RemoteException;
    void userLoggedIn(User user) throws FestivalException, RemoteException;
    void userLoggedOut(User user) throws FestivalException, RemoteException;

}
