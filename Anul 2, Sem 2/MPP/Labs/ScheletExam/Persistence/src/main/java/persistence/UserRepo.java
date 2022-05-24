package persistence;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


import javax.persistence.NoResultException;
import java.util.List;

@Component
public class UserRepo implements IUserRepository
{
    private SessionFactory sessionFactory;

    public UserRepo()
    {
        this.sessionFactory = SessionUtils.getSessionFactory();
    }

    @Override
    public User save(User entity)
    {
        try(Session session = sessionFactory.getCurrentSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
            }
        }
        return entity;
    }

    @Override
    public User update(String s, User entity)
    {
        try(Session session = sessionFactory.getCurrentSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                session.update(entity);
                tx.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
            }
        }
        return entity;
    }

    @Override
    public User delete(User entity)
    {
        try(Session session = sessionFactory.getCurrentSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                session.delete(entity);
                tx.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
            }
        }
        return entity;
    }

    @Override
    public User findOne(String s)
    {
        User foundUsers = null;
        try(Session session = sessionFactory.getCurrentSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                foundUsers = session.createQuery("FROM User WHERE id=?0" , User.class).setParameter(0 , s).getSingleResult();
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return foundUsers;
    }

    @Override
    public Iterable<User> findAll()
    {
        List<User> foundUsers = null;
        try(Session session = sessionFactory.getCurrentSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                foundUsers = session.createQuery("FROM User" , User.class).list();
                tx.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
            }
        }
        return foundUsers;
    }
}
