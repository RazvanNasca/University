package app.persistence;

import app.model.Cuvant;
import app.model.Joc;
import app.model.User;
import app.persistence.interfaces.CuvantRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class CuvantDBRepository implements CuvantRepository {

    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public CuvantDBRepository(SessionFactory sessionFactory) {
        logger.info("Initializing CuvantDBRepository with session factory: {} ", sessionFactory);
        CuvantDBRepository.sessionFactory = sessionFactory;
    }

    @Override
    public List<Cuvant> getCuvinteFromJoc(int jocId) {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Joc> jocuri = null;
            List<Cuvant> cuvinte = null;
            try {
                tx = session.beginTransaction();
                jocuri = session.createQuery("from Joc j where j.id =:jocId", Joc.class)
                        .setParameter("jocId", jocId)
                        .list();
                if(jocuri != null)
                    for(Joc j : jocuri)
                        for(Map.Entry<User,Cuvant> entry : j.getCuvinte().entrySet())
                                cuvinte.add(entry.getValue());

                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return cuvinte;
        }
    }

    @Override
    public Cuvant findOne(Integer id) {
        logger.traceEntry();
        Cuvant cuvant = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                cuvant = (Cuvant) session.get(Cuvant.class, id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return cuvant;
    }

    @Override
    public List<Cuvant> findAll() {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Cuvant> cuvinte = null;
            try {
                tx = session.beginTransaction();
                cuvinte = session.createQuery("Select c from Cuvant c", Cuvant.class)
                        .getResultList();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return cuvinte;
        }
    }

    @Override
    public Cuvant save(Cuvant entity) {
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
}
