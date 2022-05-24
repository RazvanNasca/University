package festival.model;

import java.io.Serializable;

public class Buyer extends Entity<Integer> implements Serializable {

    private String name;
    private Integer noTickets;
    private Show Show;

    public Buyer(String name, Integer noTickets, Show Show) {
        this.name = name;
        this.noTickets = noTickets;
        this.Show = Show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoTickets() {
        return noTickets;
    }

    public void setNoTickets(Integer noTickets) {
        this.noTickets = noTickets;
    }

    public Show getShow() {
        return Show;
    }

    public void setIdShow(Show newShow) {
        this.Show = newShow;
    }

    @Override
    public String toString() {
        return "Name: " + getName() +
                ", number of tickets: " + getNoTickets() +
                ", artist name: " + getShow().getArtistName();
    }
}
