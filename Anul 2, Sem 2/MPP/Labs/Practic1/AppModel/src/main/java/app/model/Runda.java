package app.model;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "Runde")
public class Runda implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JocId")
    private Joc joc;

    public Runda() {
    }

    public Runda(Joc joc) {
        this.joc = joc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Joc getJoc() {
        return joc;
    }

    public void setJoc(Joc joc) {
        this.joc = joc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Runda)) return false;
        Runda runda = (Runda) o;
        return id == runda.id && joc.equals(runda.joc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, joc);
    }

    @Override
    public String toString() {
        return "Runda{" +
                "id=" + id +
                ", joc=" + joc +
                '}';
    }
}
