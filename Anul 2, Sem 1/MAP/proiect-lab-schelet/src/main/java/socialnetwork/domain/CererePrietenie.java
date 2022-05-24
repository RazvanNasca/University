package socialnetwork.domain;

import java.time.LocalDateTime;

public class CererePrietenie extends Entity<Tuple<Long,Long>> {

    private String status;
    LocalDateTime date;

    public CererePrietenie(){}

    public String getStatus() { return status; }

    public void setStatus(String statusNou) { this.status = statusNou; }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime newDate) { this.date = newDate; }

    @Override
    public String toString() {
        return "Cererea de la " + this.getId().getLeft() + " trimisa la " + this.getId().getRight() + " avand statusul " + getStatus();
    }


}
