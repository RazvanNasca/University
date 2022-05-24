package app.persistence;

import app.model.Joc;
import app.model.User;
import app.persistence.interfaces.JocRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JocDBRepository implements JocRepository {
    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public JocDBRepository(SessionFactory sessionFactory) {
        logger.info("Initializing JocDBRepository with session factory: {} ", sessionFactory);
        JocDBRepository.sessionFactory = sessionFactory;
    }

    @Override
    public Joc findOne(Integer id) {
        logger.traceEntry();
        Joc joc = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                joc = (Joc) session.get(Joc.class, id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return joc;
    }

    @Override
    public List<Joc> findAll() {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Joc> jocuri = null;
            try {
                tx = session.beginTransaction();
                jocuri = session.createQuery("Select p from Joc p", Joc.class)
                        .getResultList();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return jocuri;
        }
    }

    @Override
    public Joc save(Joc entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            Integer id = null;
            try {
                tx = session.beginTransaction();
                id = (Integer) session.save(entity);
                tx.commit();
                entity.setId(id);
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            }
            return entity;
        }
    }
}
