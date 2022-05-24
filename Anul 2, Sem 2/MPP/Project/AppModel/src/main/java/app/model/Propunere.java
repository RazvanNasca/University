package app.model;

public class Propunere  implements java.io.Serializable
{
    private String sirCaractere;

    private User user;

    private Joc joc;

    public Propunere(String sirCaractere, User user, Joc joc)
    {
        this.sirCaractere = sirCaractere;
        this.user = user;
        this.joc = joc;
    }

    public String getSirCaractere() {
        return sirCaractere;
    }

    public void setSirCaractere(String sirCaractere) {
        this.sirCaractere = sirCaractere;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Joc getJoc() {
        return joc;
    }

    public void setJoc(Joc joc) {
        this.joc = joc;
    }

    @Override
    public String toString()
    {
        return "Hand {" +
                "sirul initial = " + sirCaractere +
                ", user = " + user.getUsername() +
                ", joc = " + joc.getId() +
                '}';
    }
}
