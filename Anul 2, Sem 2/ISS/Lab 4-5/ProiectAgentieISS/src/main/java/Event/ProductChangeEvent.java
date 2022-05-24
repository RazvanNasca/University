package Event;

import model.Product;

import java.util.*;

/**
 * 
 */
public class ProductChangeEvent {

    /**
     * Default constructor
     */
    public ProductChangeEvent() {
    }

    /**
     * 
     */
    public EventType type;

    /**
     * 
     */
    private Product data;

    /**
     * 
     */
    public Product oldData;



    /**
     * @return
     */
    public EventType getType() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Product getData() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Product getOldData() {
        // TODO implement here
        return null;
    }

}