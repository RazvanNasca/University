package app.services;

import app.model.User;

public interface IAppServices
{
    User login(String username, String password, IAppObserver client) throws AppException;
    void logout(User user);
    void startGame(User user, String word) throws AppException;
}
