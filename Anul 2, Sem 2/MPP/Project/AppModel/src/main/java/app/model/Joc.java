package app.model;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

public class Joc implements java.io.Serializable
{
    private int id;

    public Joc() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Game {" +
                "id = " + id + " }";
    }
}
