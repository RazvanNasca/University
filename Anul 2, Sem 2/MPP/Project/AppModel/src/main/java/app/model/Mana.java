package app.model;

public class Mana implements java.io.Serializable
{
    private Runda runda;

    private User user;

    private String propunere;

    private int puncte;

    private User userGhicit;

    public Mana(Runda runda, User user, String propunere, int puncte, User userGhicit) {
        this.runda = runda;
        this.user = user;
        this.propunere = propunere;
        this.puncte = puncte;
        this.userGhicit = userGhicit;
    }

    public Runda getRunda() {
        return runda;
    }

    public void setRunda(Runda runda) {
        this.runda = runda;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPropunere() {
        return propunere;
    }

    public void setPropunere(String propunere) {
        this.propunere = propunere;
    }

    public int getPuncte() {
        return puncte;
    }

    public void setPuncte(int puncte) {
        this.puncte = puncte;
    }

    public User getUserGhicit() {
        return userGhicit;
    }

    public void setUserGhicit(User userGhicit) {
        this.userGhicit = userGhicit;
    }


    @Override
    public String toString()
    {
        return "Hand {" +
                "runda = " + runda.getId() +
                ", user = " + user.getUsername() +
                ", puncte = " + puncte +
                ", user ghicit = " + userGhicit.getUsername() +
                '}';
    }

}
