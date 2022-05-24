package repository.orm;

import festival.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.RepoUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepoUserORM implements RepoUser {

    private static SessionFactory sessionFactory;

    public RepoUserORM()
    {
        initialize();
    }

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

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    @Override
    public User findOne(String name) {
        //initialize();

        User u = null;

        try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            for (User user : users) {
                if(user.getName().equals(name))
                    u = new User(user.getName(), user.getPassword());
            }
            session.getTransaction().commit();
        }

       // close();

        if(u != null)
            System.out.println("User: " + u.getName() + " password " + u.getPassword());
        else
            System.out.println("User null!!");

        return u;
    }

    @Override
    public List<User> findAll() {
       // initialize();

        List<User> users;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            for (User user : users) {
                System.out.println("User: " + user.getName() + " password " + user.getPassword());
            }
            session.getTransaction().commit();
        }

        return users;
    }

    @Override
    public User save(User user) {
       // initialize();

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user.getName(), user.getPassword());
            session.getTransaction().commit();
        }

       // close();

        return user;
    }

    @Override
    public User findByName(String name) {
        User u = null;

        try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            for (User user : users) {
                if(user.getName().equals(name))
                    u = new User(user.getName(), user.getPassword());
            }
            session.getTransaction().commit();
        }

        return u;
    }
}
