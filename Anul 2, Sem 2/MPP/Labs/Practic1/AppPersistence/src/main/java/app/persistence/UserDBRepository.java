package app.persistence;


import app.model.User;
import app.persistence.interfaces.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDBRepository implements UserRepository {

    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public UserDBRepository(SessionFactory sessionFactory) {
        logger.info("Initializing UserDBRepository with session factory: {} ", sessionFactory);
        UserDBRepository.sessionFactory = sessionFactory;
    }

    @Override
    public User findOne(String id) {
        logger.traceEntry();
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = (User) session.get(User.class, id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return user;
    }

    @Override
    public List<User> findAll() {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<User> users = null;
            try {
                tx = session.beginTransaction();
                users = session.createQuery("Select p from User p", User.class)
                        .getResultList();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return users;
        }
    }

    @Override
    public User save(User entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            String id = null;
            try {
                tx = session.beginTransaction();
                id = (String) session.save(entity);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            }
            return entity;
        }
    }

    public User getUser(String username, String password) {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List users = null;
            try {
                tx = session.beginTransaction();
                users = session.createQuery("FROM User u where u.username=:username and u.password=:password", User.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            if (users.size() > 0) {
                logger.traceExit();
                return (User) users.get(0);
            }
        }
        logger.traceExit();
        return null;
    }
}