package app.persistence.interfaces;

import app.model.Joc;
import app.model.Mana;
import app.model.ManaDTO;
import app.model.ManaId;

import java.util.List;

public interface ManaRepository extends Repository<ManaId, Mana>{

    List<ManaDTO> getMainiByJucatorSiJoc(int nrJoc, String username);

}
