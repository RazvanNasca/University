package repository.orm;

import festival.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class TestUserORM {
    private static SessionFactory sessionFactory;

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static void main(String ... arg) {
        initialize();

        User user = new User("Test3", "0000");

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user.getName(), user);
            session.getTransaction().commit();
        }

        List<User> users;

        try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            for (User u : users) {
                System.out.println("User: " + u.getName() + " password " + u.getPassword());
            }
            session.getTransaction().commit();
        }

        close();
    }

}
