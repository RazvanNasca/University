package app.persistence.interfaces;

import app.model.Cuvant;

import java.util.List;

public interface CuvantRepository extends Repository<Integer, Cuvant>{

    List<Cuvant> getCuvinteFromJoc(int jocId);

}
