package persistence;

public interface IRepository<ID , T>
{
    T save(T entity);
    T update(ID id , T entity);
    T delete(T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}
