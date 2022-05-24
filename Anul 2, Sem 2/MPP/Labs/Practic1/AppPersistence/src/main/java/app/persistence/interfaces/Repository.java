package app.persistence.interfaces;

import java.util.List;

public interface Repository<ID, E> {
    /**
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException if id is null.
     */
    E findOne(ID id);

    /**
     * @return all entities
     */
    List<E> findAll();

    E save(E entity);
}