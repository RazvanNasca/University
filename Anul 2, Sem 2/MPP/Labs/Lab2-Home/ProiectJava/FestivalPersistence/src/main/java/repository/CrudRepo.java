package repository;


import festival.model.Entity;

import java.util.List;

public interface CrudRepo <ID, E extends Entity<ID>> {

    E findOne(ID id);

    List<E> findAll();

    E save(E e);
}
