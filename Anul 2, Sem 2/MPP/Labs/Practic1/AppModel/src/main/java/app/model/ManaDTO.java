package app.model;

import java.util.List;
import java.util.Objects;

public class ManaDTO implements java.io.Serializable{
    private String username;
    private String jucator;
    private String litera;
    private int punctaj;
    private String cuvInitial;

    public ManaDTO(String username, String litera, int punctaj) {
        this.username = username;
        this.litera = litera;
        this.punctaj = punctaj;
    }

    public ManaDTO(String username, int punctaj) {
        this.username = username;
        this.punctaj = punctaj;
    }

    public ManaDTO(String username, String cuvInitial) {
        this.username = username;
        this.cuvInitial = cuvInitial;
    }

    public ManaDTO(String username, String jucator, String litera, int punctaj) {
        this.username = username;
        this.jucator = jucator;
        this.litera = litera;
        this.punctaj = punctaj;
    }

    public String getJucator() {
        return jucator;
    }

    public void setJucator(String jucator) {
        this.jucator = jucator;
    }

    public String getCuvInitial() {
        return cuvInitial;
    }

    public void setCuvInitial(String cuvInitial) {
        this.cuvInitial = cuvInitial;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getLitera() {
        return litera;
    }

    public void setPozitie(String litera) {
        this.litera = litera;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManaDTO)) return false;
        ManaDTO manaDTO = (ManaDTO) o;
        return litera.equals(manaDTO.litera) && punctaj == manaDTO.punctaj && username.equals(manaDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, litera, punctaj);
    }

    @Override
    public String toString() {
        return "ManaDTO{" +
                "username ='" + username + '\'' +
                ", litera =" + litera +
                ", punctaj =" + punctaj +
                '}';
    }
}
