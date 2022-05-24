package Service;

import java.util.*;

/**
 * 
 */
public class Service extends Observable {

    /**
     * Default constructor
     */
    public Service() {
    }

    /**
     * 
     */
    public void Attribute1;

    /**
     * 
     */
    private RepoUser userRepo;

    /**
     * 
     */
    public RepoProduct productRepo;

    /**
     * 
     */
    public List<Observer<ProductChangeEvent>> observers;



    /**
     * @param user 
     * @return
     */
    public User login(User user) {
        // TODO implement here
        return null;
    }

    /**
     * @param user 
     * @return
     */
    public User logout(User user) {
        // TODO implement here
        return null;
    }

    /**
     * @param user 
     * @return
     */
    public User findUser(User user) {
        // TODO implement here
        return null;
    }

    /**
     * @param user 
     * @return
     */
    public User addUser(User user) {
        // TODO implement here
        return null;
    }

    /**
     * @param user 
     * @return
     */
    public User updateUser(User user) {
        // TODO implement here
        return null;
    }

    /**
     * @param user 
     * @return
     */
    public User deleteUser(User user) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<User> findAllUsers() {
        // TODO implement here
        return null;
    }

    /**
     * @param p 
     * @return
     */
    public Product findProduct(Product p) {
        // TODO implement here
        return null;
    }

    /**
     * @param p 
     * @return
     */
    public Product addProduct(Product p) {
        // TODO implement here
        return null;
    }

    /**
     * @param p 
     * @return
     */
    public Product updateProduct(Product p) {
        // TODO implement here
        return null;
    }

    /**
     * @param p 
     * @return
     */
    public Product deleteProduct(Product p) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<Product> findAllProducts() {
        // TODO implement here
        return null;
    }

    /**
     * @param obs 
     * @return
     */
    public void addObserver(Observer<ProductChangeEvent> obs) {
        // TODO implement here
        return null;
    }

    /**
     * @param obs 
     * @return
     */
    public void removeObserver(Observer<ProductChangeEvent> obs) {
        // TODO implement here
        return null;
    }

    /**
     * @param event 
     * @return
     */
    public void notifyObservers(ProductChangeEvent event) {
        // TODO implement here
        return null;
    }

}