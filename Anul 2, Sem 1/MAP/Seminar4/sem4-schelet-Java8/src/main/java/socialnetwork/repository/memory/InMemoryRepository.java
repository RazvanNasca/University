package socialnetwork.repository.memory;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }


    @Override
    public Optional<E> findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<E> findAll()
    {
        Set <E> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;

    }

    @Override
    public Optional<E> save(E entity)
    {
        if(entity == null)
        {
            throw new IllegalArgumentException("Id-ul nu poate fi nul");
        }

        validator.validate(entity);

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id)
    {
        if(id == null)
            throw new IllegalArgumentException("Nu are cum sa fie null\n");

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity)
    {
        if(entity == null)
            throw new IllegalArgumentException("Entitate nu are voie sa fie null!\n");

        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(),(k,v) -> entity));
    }

}
