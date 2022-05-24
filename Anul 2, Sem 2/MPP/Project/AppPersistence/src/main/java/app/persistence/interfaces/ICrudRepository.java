package app.persistence.interfaces;

import java.util.List;

public interface ICrudRepository<ID, E>
{
    E findOne(ID id);
    List<E> findAll();
    E save(E entity);
}