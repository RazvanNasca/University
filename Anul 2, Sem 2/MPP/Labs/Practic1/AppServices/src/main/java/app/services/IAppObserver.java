package app.services;

import app.model.Mana;
import app.model.ManaDTO;
import app.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IAppObserver extends Remote {
    void jocNou(List<ManaDTO> users) throws RemoteException;
    void sfRunda(List<ManaDTO> pozitii) throws RemoteException;
    void clasament(List<ManaDTO> clasament) throws RemoteException;
}