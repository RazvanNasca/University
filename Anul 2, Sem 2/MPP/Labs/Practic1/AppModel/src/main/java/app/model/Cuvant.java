package app.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "Cuvinte")
/*@AssociationOverrides({
        @AssociationOverride(name = "pk.cuvant",
                joinColumns = @JoinColumn(name = "cuvant")),
        @AssociationOverride(name = "pk.UserId",
                joinColumns = @JoinColumn(name = "UserId")) })*/
public class Cuvant implements java.io.Serializable{

    @Id
    /*@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdCuvant")
    private int idCuvant;*/

    @Column(name = "cuvant")
    private String cuvant;

    @Id
    @Column(name = "UserId")
    private String userId;

    public Cuvant() {
    }

    public Cuvant(String cuvant, String userId) {
        this.cuvant = cuvant;
        this.userId = userId;
    }

    /*public int getIdCuvant() {
        return idCuvant;
    }

    public void setIdCuvant(int idCuvant) {
        this.idCuvant = idCuvant;
    }*/

    public String getCuvant() {
        return cuvant;
    }

    public void setCuvant(String cuvant) {
        this.cuvant = cuvant;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(cuvant, userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "cuvant ='" + cuvant + '\'' +
                ", user ='" + userId + '\'' +
                '}';
    }

}

