package Service;

import java.util.*;

/**
 * 
 */
public interface Observer {

    /**
     * @param event 
     * @return
     */
    public void update(E event);

}