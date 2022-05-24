package app.model;

import javax.persistence.*;
import java.util.Objects;

public class Runda implements java.io.Serializable
{
    private int id;

    private Joc joc;

    public Runda() { }

    public Runda(Joc joc) {
        this.joc = joc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Joc getGame() {
        return joc;
    }

    public void setGame(Joc joc) {
        this.joc = joc;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Runda))
            return false;
        Runda runda = (Runda) o;
        return id == runda.id && joc.equals(runda.joc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, joc);
    }

    @Override
    public String toString()
    {
        return "Round {" +
                "id = " + id +
                ", game = " + joc +
                '}';
    }
}
