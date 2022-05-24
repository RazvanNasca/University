package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {

    @Override
    public void validateId(String Id) throws ValidationException {

        if(Id.matches(".*\\D+.*"))
            throw new ValidationException("Id contine litere!");

    }

    @Override
    public void validate(Utilizator entity) throws ValidationException {

        String FirstName = entity.getFirstName();
        String LastName = entity.getLastName();

        if(FirstName.equals(""))
            throw new ValidationException("Nume vid");

        if(LastName.equals(""))
            throw new ValidationException("Nume vid");

        int first = FirstName.length();
        int last = LastName.length();


        if(FirstName.matches(".*\\d+.*"))
            throw new ValidationException("Numele contine cifre!");

        if(LastName.matches(".*\\d+.*"))
            throw new ValidationException("Prenumele contine cifre!");

    }
}
