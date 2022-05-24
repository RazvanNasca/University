package app.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
public class ManaId implements java.io.Serializable{
    @ManyToOne
    @JoinColumn(name = "Username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "RundaId")
    private Runda runda;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Runda getRunda() {
        return runda;
    }

    public void setRunda(Runda runda) {
        this.runda = runda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManaId)) return false;
        ManaId manaId = (ManaId) o;
        return user.equals(manaId.user) && runda.equals(manaId.runda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, runda);
    }
}
