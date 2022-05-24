package repository;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ICrudUser;

import java.util.*;

/**
 * 
 */
public class RepoUser implements ICrudUser {

    /**
     * Default constructor
     */
    private static SessionFactory sessionFactory;
    public RepoUser() { initialize();}

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    /**
     * @return
     */
    public List<User> findAll() {
        List<User> users;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            for (User user : users) {
                System.out.println("User: " + user.getUsername() + " password " + user.getPassword());
            }
            session.getTransaction().commit();
        }

        return users;
    }

    @Override
    public User findOne(String s)
    {
        User u = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            for (User user : users) {
                if(user.getUsername().equals(s))
                    u = new User(user.getUsername(), user.getPassword(), user.getType());
            }
            session.getTransaction().commit();
        }


        if(u != null)
            System.out.println("User: " + u.getUsername() + " password " + u.getPassword());
        else
            System.out.println("User null!!");

        return u;
    }

    /**
     * @param s
     * @return
     */
    public User findByUsername(String s) {
        User u = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            for (User user : users) {
                if(user.getUsername().equals(s))
                    u = new User(user.getUsername(), user.getPassword(), user.getType());
            }
            session.getTransaction().commit();
        }


        if(u != null)
            System.out.println("User: " + u.getUsername() + " password " + u.getPassword());
        else
            System.out.println("User null!!");

        return u;
    }

    /**
     * @param user
     * @return
     */
    public void save(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user.getUsername(), user);
            session.getTransaction().commit();
        }
    }

    /**
     * @param user
     * @return
     */
    public void update(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user.getUsername(), user);
            session.getTransaction().commit();
        }
    }

    /**
     * @param user
     * @return
     */
    public void delete(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(user.getUsername(), user);
            session.getTransaction().commit();
        }
    }


}