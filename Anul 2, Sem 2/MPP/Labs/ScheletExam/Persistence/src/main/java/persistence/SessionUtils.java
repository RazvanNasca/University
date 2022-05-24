package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionUtils
{
    private static SessionFactory sessionFactory = null;
    private SessionUtils(){}

    private static void initSession()
    {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println("dadadada");
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            initSession();
        return sessionFactory;
    }
}
