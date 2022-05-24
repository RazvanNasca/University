package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;

public class PrietenieValidator implements Validator<Prietenie>{

    public void validate(Prietenie entity) throws ValidationException {

        Long id1 = entity.getId().getLeft();
        Long id2 = entity.getId().getRight();

        if(id1.equals(id2))
            throw new ValidationException("Id-ul nu poate sa fie acelasi!");

    }

    public void validateId(String Id) throws ValidationException {

        if(Id.matches(".*\\D+.*"))
            throw new ValidationException("Id contine litere!");

    }

}
