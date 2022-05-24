package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Entity<ID> implements Serializable {

    /**
     * Default constructor
     */
    public Entity() {
    }

    /**
     * 
     */
    private ID id;


    /**
     * @return
     */
    public ID getID() {
        // TODO implement here
        return this.id;
    }

    /**
     * @param id 
     * @return
     */
    public void setID(ID id) {
        this.id = id;
    }

}