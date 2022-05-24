package Repositoy.Interface;

import java.util.*;

/**
 * 
 */
public interface ICrudUser extends ICrudRepo {



    /**
     * 
     */
    public void Operation1();

    /**
     * @param u 
     * @return
     */
    public User findByUsername(String u);

}