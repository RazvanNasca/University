package repository;


import java.util.List;

public interface CrudRepo<ID, E> {

    E findOne(ID id);

    List<E> findAll();

    E save(E e);
}
