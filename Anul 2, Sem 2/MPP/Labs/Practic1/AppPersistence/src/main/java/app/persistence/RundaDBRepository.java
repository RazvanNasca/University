package app.persistence;

import app.model.Joc;
import app.model.Runda;
import app.persistence.interfaces.RundaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


public class RundaDBRepository implements RundaRepository {
    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public RundaDBRepository(SessionFactory sessionFactory) {
        logger.info("Initializing RundaDBRepository with session factory: {} ", sessionFactory);
        RundaDBRepository.sessionFactory = sessionFactory;
    }

    @Override
    public Runda findOne(Integer id) {
        logger.traceEntry();
        Runda runda = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                runda = (Runda) session.get(Runda.class, id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return runda;
    }

    @Override
    public List<Runda> findAll() {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Runda> runde = null;
            try {
                tx = session.beginTransaction();
                runde = session.createQuery("Select p from Runda p", Runda.class)
                        .getResultList();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return runde;
        }
    }

    @Override
    public Runda save(Runda entity) {
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
