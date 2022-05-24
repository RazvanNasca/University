package service;

import model.Product;
import model.User;
import repository.RepoProduct;
import repository.RepoUser;

import java.util.*;
/**
 * 
 */
public class Service {

    /**
     * Default constructor
     */
    public Service() {
    }

    /**
     * 
     */
    private RepoUser userRepo;

    /**
     * 
     */
    public RepoProduct productRepo;


    /**
     * @param user 
     * @return
     */
    public User login(User user) {
        User userVerif = this.userRepo.findByUsername(user.getUsername());

        if(userVerif != null)
            if(userVerif.getPassword().equals(user.getPassword()) && userVerif.getType() == user.getType())
                return user;
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
        return this.userRepo.findOne(user.getUsername());
    }

    /**
     * @param user 
     * @return
     */
    public User addUser(User user) {
        this.userRepo.save(user);
        return user;
    }

    /**
     * @param user 
     * @return
     */
    public User updateUser(User user) {
        this.userRepo.update(user);
        return user;
    }

    /**
     * @param user 
     * @return
     */
    public User deleteUser(User user) {
        this.userRepo.delete(user);
        return user;
    }

    /**
     * @return
     */
    public List<User> findAllUsers() {
        return this.userRepo.findAll();
    }

    /**
     * @param p 
     * @return
     */
    public Product findProduct(Product p) {
        return this.productRepo.findByName(p.getName());
    }

    /**
     * @param p 
     * @return
     */
    public Product addProduct(Product p) {
        this.productRepo.save(p);
        return p;
    }

    /**
     * @param p 
     * @return
     */
    public Product updateProduct(Product p) {
        this.productRepo.update(p);
        return p;
    }

    /**
     * @param p 
     * @return
     */
    public Product deleteProduct(Product p) {
        this.productRepo.delete(p);
        return p;
    }

    /**
     * @return
     */
    public List<Product> findAllProducts() {
        return this.productRepo.findAll();
    }

}