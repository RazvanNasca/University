package socialnetwork.domain.validators;

import socialnetwork.domain.Mesaje;
import socialnetwork.domain.Prietenie;

public class MesajeValidator implements Validator<Mesaje>{

    public void validate(Mesaje entity) throws ValidationException {

        String continut = entity.getMesaj();

        if(continut.equals(""))
            throw new ValidationException("Mesajul nu poate sa fie gol!");

    }

    public void validateId(String Id) throws ValidationException {

        if(Id.matches(".*\\D+.*"))
            throw new ValidationException("Id contine litere!");

    }

}
