package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class PrietenieFile extends AbstractFileRepository<Tuple<Long,Long>, Prietenie>{

    public PrietenieFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        //TODO: implement method
        Prietenie prietenie = new Prietenie();
        prietenie.setId(new Tuple(Long.parseLong(attributes.get(0)),Long.parseLong(attributes.get(1))));
        prietenie.setDate(LocalDateTime.parse(attributes.get(2)));

        return prietenie;
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft()+";"+entity.getId().getRight()+";"+entity.getDate();
    }

}
