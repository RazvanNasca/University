package app.model;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "Jocuri")
@Embeddable
public class Joc implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Cuvinte")
    @Column(name="Cuvant")
    @MapKeyJoinColumn(name = "UserId")
    @MapKeyClass(User.class)
    private Map<User, Cuvant> cuvinte;

    public Joc() {
    }

    public Joc(Map<User, Cuvant> cuvante) {
        this.cuvinte = cuvante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<User, Cuvant> getCuvinte() {
        return cuvinte;
    }

    public void setCuvant(Map<User, Cuvant> cuvinte) {
        this.cuvinte = cuvinte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joc)) return false;
        Joc joc = (Joc) o;
        return id == joc.id && cuvinte.equals(joc.cuvinte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cuvinte);
    }

    @Override
    public String toString() {
        return "Joc{" +
                "id = " + id +
                ", cuvant = " + cuvinte +
                '}';
    }
}
