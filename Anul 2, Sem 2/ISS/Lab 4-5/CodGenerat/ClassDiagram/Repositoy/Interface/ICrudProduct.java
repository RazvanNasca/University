package Repositoy.Interface;

import java.util.*;

/**
 * 
 */
public interface ICrudProduct extends ICrudRepo {



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