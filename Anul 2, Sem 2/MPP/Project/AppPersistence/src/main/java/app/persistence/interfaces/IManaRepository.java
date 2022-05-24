package app.persistence.interfaces;

import app.model.Mana;
import app.model.Runda;
import app.model.User;

import java.util.List;

public interface IManaRepository extends ICrudRepository<Integer, Mana>
{
    Mana findOneByRundaAndUser(Runda runda, User user);

    List<Mana> findAllByRundaAndUser(Runda runda, User user);

    List<Mana> getHandByGameAndUser(int nrJoc, String username);
}
