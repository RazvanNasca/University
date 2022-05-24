package app.persistence.interfaces;

import app.model.Mana;
import app.model.Propunere;

import java.util.List;
import java.util.Map;

public interface IPropunereRepository extends ICrudRepository<Integer, Propunere>
{
    Map<String, String> getPropuneriByJoc(int idJoc);
}
