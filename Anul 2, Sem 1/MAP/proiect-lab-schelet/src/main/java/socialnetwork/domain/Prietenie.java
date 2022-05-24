package socialnetwork.domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

    public Prietenie() {}

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime newDate)
    {
        this.date = newDate;
    }

    @Override
    public String toString() {
        return "Primul: " + this.getId().getLeft() + ", al doilea: " + this.getId().getRight();
    }
}
