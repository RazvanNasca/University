package socialnetwork.repository.memory;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    protected Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("Id must be not null\n");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("Entity must be not null\n");
        validator.validate(entity);

        if(entities.get(entity.getId()) != null)
            throw new RepoException("Id existent!\n");

        entities.put(entity.getId(),entity);

        return null;
    }

    @Override
    public E delete(ID id)
    {
        if(id == null)
            throw new IllegalArgumentException("Id-ul nu poate sa fie null!\n");

        return entities.remove(id);
    }

    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!\n");
        validator.validate(entity);

        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }

}
