package repository;

import model.User;
import repository.ICrudRepo;

/**
 * 
 */
public interface ICrudUser extends ICrudRepo<String, User> {


    /**
     * @param u 
     * @return
     */
    public User findByUsername(String u);

}