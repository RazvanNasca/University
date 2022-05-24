package Service;

import java.util.*;

/**
 * 
 */
public interface Observable {

    /**
     * @param obs 
     * @return
     */
    public void addObserver(Observer<E> obs);

    /**
     * @param obs 
     * @return
     */
    public void removeObserver(Observer<E> obs);

    /**
     * @param event 
     * @return
     */
    public void notifyObservers(E event);

}