package app.model;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "Maini")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "Username")),
        @AssociationOverride(name = "pk.runda",
                joinColumns = @JoinColumn(name = "RundaId")) })
public class Mana implements java.io.Serializable {
    @EmbeddedId
    private ManaId pk;

    @Column(name = "Litera")
    private String litera;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JucatorUsername")
    private User jucator;

    @Column(name = "Puncte")
    private int puncte;

    public Mana() {
    }

    public User getJucator() {
        return jucator;
    }

    public void setJucator(User jucator) {
        this.jucator = jucator;
    }

    public Mana(String litera, User jucator, int puncte) {
        this.litera = litera;
        this.jucator = jucator;
        this.puncte = puncte;
    }

    @Transient
    public User getUser(){
        return getPk().getUser();
    }

    public void setUser(User user){
        getPk().setUser(user);
    }

    @Transient
    public Runda getRunda(){
        return getPk().getRunda();
    }

    public void setRunda(Runda runda){
        getPk().setRunda(runda);
    }

    public ManaId getPk() {
        return pk;
    }

    public void setPk(ManaId pk) {
        this.pk = pk;
    }


    public String getLitera() {
        return litera;
    }

    public void setPozitie(String litera) {
        this.litera = litera;
    }

    public int getPuncte() {
        return puncte;
    }

    public void setPuncte(int puncte) {
        this.puncte = puncte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mana)) return false;
        Mana mana = (Mana) o;
        return litera.equals(mana.litera) && puncte == mana.puncte && pk.equals(mana.pk) && jucator.equals(mana.jucator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, litera, jucator, puncte);
    }

    @Override
    public String toString() {
        return "Mana{" +
                "pk=" + pk +
                ", litera=" + litera +
                ", jucator=" + jucator +
                ", puncte=" + puncte +
                '}';
    }
}
