package repository;

import model.AppUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class RepoUser implements IRepoUser {

    private static SessionFactory sessionFactory;

    public RepoUser()
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
    public AppUser findOne(String name) {

        AppUser u = null;

        try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            List<AppUser> users = session.createQuery("FROM User", AppUser.class).list();
            for (AppUser user : users) {
                if(user.getName().equals(name))
                    u = new AppUser(user.getName(), user.getPassword());
            }
            session.getTransaction().commit();
        }

        if(u != null)
            System.out.println("User: " + u.getName() + " password " + u.getPassword());
        else
            System.out.println("User null!!");

        return u;
    }

    @Override
    public List<AppUser> findAll() {
        List<AppUser> users;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            users = session.createQuery("FROM User", AppUser.class).list();
            for (AppUser user : users) {
                System.out.println("User: " + user.getName() + " password " + user.getPassword());
            }
            session.getTransaction().commit();
        }

        return users;
    }

    @Override
    public AppUser save(AppUser user) {

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user.getName(), user.getPassword());
            session.getTransaction().commit();
        }

        return user;
    }

    @Override
    public AppUser findByName(String name) {
        AppUser u = null;

        try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            List<AppUser> users = session.createQuery("FROM User", AppUser.class).list();
            for (AppUser user : users) {
                if(user.getName().equals(name))
                    u = new AppUser(user.getName(), user.getPassword());
            }
            session.getTransaction().commit();
        }

        return u;
    }
}
