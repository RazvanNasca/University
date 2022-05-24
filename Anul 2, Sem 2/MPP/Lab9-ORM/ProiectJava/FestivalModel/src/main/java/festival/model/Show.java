package festival.model;

import java.time.LocalDateTime;

public class Show extends Entity<Integer>{

    private String artistName;
    private LocalDateTime data;
    private String place;
    private Integer remainingTickets;

    public Show(String artistName, LocalDateTime data, String place, Integer remainingTickets)
    {
        this.artistName = artistName;
        this.data = data;
        this.place = place;
        this.remainingTickets = remainingTickets;
    }

    public Integer getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(Integer remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Name of artist: " + getArtistName() +
                ", data: " + getData() +
                ", place: " + getPlace() +
                ", remaining Tickets: " + getRemainingTickets();
    }
}
