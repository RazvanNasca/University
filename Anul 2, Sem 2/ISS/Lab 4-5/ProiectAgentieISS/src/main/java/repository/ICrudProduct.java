package repository;

import java.util.*;

import model.Product;

/**
 * 
 */
public interface ICrudProduct extends ICrudRepo<String, Product> {


    /**
     * @param n 
     * @return
     */
    public Product findByName(String n);

    /**
     * @return
     */
    public List<Product> orderByPrice();

}