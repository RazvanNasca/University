package Service;

import java.util.*;

/**
 * 
 */
public interface IService {



    /**
     * @param user 
     * @return
     */
    public User login(User user);

    /**
     * @param user 
     * @return
     */
    public User logout(User user);

    /**
     * @param user 
     * @return
     */
    public User findUser(User user);

    /**
     * @param user 
     * @return
     */
    public User updateUser(User user);

    /**
     * @param user 
     * @return
     */
    public User deleteUser(User user);

    /**
     * @param user 
     * @return
     */
    public User addUser(User user);

    /**
     * @return
     */
    public List<User> findAllUsers();

    /**
     * @param p 
     * @return
     */
    public Product findProduct(Product p);

    /**
     * @param p 
     * @return
     */
    public Product updateProduct(Product p);

    /**
     * @param p 
     * @return
     */
    public Product deleteProduct(Product p);

    /**
     * @param p 
     * @return
     */
    public Product addProduct(Product p);

    /**
     * @return
     */
    public List<Product> findAllProducts();

    /**
     * @param u 
     * @return
     */
    public User findUserByUsername(String u);

    /**
     * @param n 
     * @return
     */
    public Product findProductByName(String n);

    /**
     * @return
     */
    public List<Product> orderByPrice();

}