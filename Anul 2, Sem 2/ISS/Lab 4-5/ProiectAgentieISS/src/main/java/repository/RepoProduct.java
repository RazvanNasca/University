package repository;

import model.Product;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ICrudProduct;

import java.util.*;

/**
 * 
 */
public class RepoProduct implements ICrudProduct {

    private static SessionFactory sessionFactory;
    /**
     * Default constructor
     */
    public RepoProduct() {
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

    /**
     * @return
     */
    public List<Product> findAll() {
        List<Product> prods;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            prods = session.createQuery("FROM Product", Product.class).list();
            for (Product prod : prods) {
                System.out.println("Product: " + prod.getName());
            }
            session.getTransaction().commit();
        }

        return prods;
    }

    @Override
    /**
     * @param p
     * @return
     */
    public void save(Product p) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(p.getName(), p);
            session.getTransaction().commit();
        }
    }

    /**
     * @param p
     * @return
     */
    public void update(Product p) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(p.getName(), p);
            session.getTransaction().commit();
        }
    }

    /**
     * @param p
     * @return
     */
    public void delete(Product p) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(p.getName(), p);
            session.getTransaction().commit();
        }
    }

    /**
     * @param u 
     * @return
     */
    public Product findByName(String u) {
        Product p = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Product> prods = session.createQuery("FROM Product", Product.class).list();
            for (Product prod : prods) {
                if(prod.getName().equals(u))
                    p = new Product(prod.getName(), prod.getPrice(), prod.getQuantity());
            }
            session.getTransaction().commit();
        }


        if(p != null)
            System.out.println("Product: " + p.getName());
        else
            System.out.println("User null!!");

        return p;
    }


    @Override
    public Product findOne(String u)
    {
        Product p = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Product> prods = session.createQuery("FROM Product", Product.class).list();
            for (Product prod : prods) {
                if(prod.getName().equals(u))
                    p = new Product(prod.getName(), prod.getPrice(), prod.getQuantity());
            }
            session.getTransaction().commit();
        }


        if(p != null)
            System.out.println("Product: " + p.getName());
        else
            System.out.println("User null!!");

        return p;
    }

    /**
     * @return
     */
    public List<Product> orderByPrice() {
        // TODO implement here
        return null;
    }


}