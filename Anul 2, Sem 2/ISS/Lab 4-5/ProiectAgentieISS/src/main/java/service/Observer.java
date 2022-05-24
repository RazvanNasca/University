package service;

import model.Product;
import model.User;

/**
 * 
 */
public interface Observer {

    void update(Product prod);
    void userLoggedIn(User user);
    void userLoggedOut(User user);

}