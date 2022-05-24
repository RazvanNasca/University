package app.services;

import app.model.User;

public interface IAppServices {
    User login(String username, String password, IAppObserver client) throws AppException;
    void logout(User user);
    void startJoc(User user, String cuvant) throws AppException;
    void trimiteMana(User user, String jucatorUsername, String litera) throws AppException;
}
