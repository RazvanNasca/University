package model;

/**
 * 
 */
public class User extends Entity<String> {

    /**
     * Default constructor
     */

    public User(){

    }

    public User(String username, String password, UserType type)
    {
        this.setID(username);
        this.username = username;
        this.password = password;
        this.type = type;
    }

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private UserType type;

    /**
     * 
     */
    private String password;



    /**
     * @return
     */
    public String getUsername() {
        // TODO implement here
        return this.username;
    }

    /**
     * @return
     */
    public UserType getType() {
        // TODO implement here
        return this.type;
    }

    /**
     * @return
     */
    public String getPassword() {
        // TODO implement here
        return this.password;
    }

    /**
     * @param u 
     * @return
     */
    public void setUsername(String u) {
        // TODO implement here
        this.username = u;
    }

    /**
     * @param t 
     * @return
     */
    public void setType(UserType t) {
        // TODO implement here
        this.type = t;
    }

    /**
     * @param s 
     * @return
     */
    public void setPassword(String s) {
        // TODO implement here
        this.password = s;
    }

}