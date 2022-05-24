package app.persistence;

import app.model.Mana;
import app.model.ManaDTO;
import app.model.ManaId;
import app.persistence.interfaces.ManaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManaDBRepository implements ManaRepository {
    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public ManaDBRepository(SessionFactory sessionFactory) {
        logger.info("Initializing ManaDBRepository with session factory: {} ", sessionFactory);
        ManaDBRepository.sessionFactory = sessionFactory;
    }

    @Override
    public Mana findOne(ManaId id) {
        logger.traceEntry();
        Mana Mana = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Mana = (Mana) session.get(Mana.class, id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return Mana;
    }

    @Override
    public List<Mana> findAll() {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Mana> Manas = null;
            try {
                tx = session.beginTransaction();
                Manas = session.createQuery("Select p from Mana p", Mana.class)
                        .getResultList();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return Manas;
        }
    }

    @Override
    public Mana save(Mana entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            ManaId id = null;
            try {
                tx = session.beginTransaction();
                id = (ManaId) session.save(entity);
                entity.setPk(id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            }
            return entity;
        }
    }

    public List<ManaDTO> getMainiByJucatorSiJoc(int nrJoc, String username){
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<ManaDTO> maini = null;
            try {
                tx = session.beginTransaction();
                List<Mana> maini2 = session.createQuery("select m from Mana m where m.pk.user.username=:username and m.pk.runda.joc.id=:nrJoc", Mana.class)
                        .setParameter("username", username)
                        .setParameter("nrJoc", nrJoc)
                        .getResultList();
                maini = maini2.stream().map(m-> new ManaDTO(username, m.getJucator().getUsername(), m.getLitera(), m.getPuncte())).collect(Collectors.toList());
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                logger.error(e);
                e.printStackTrace();
            }
            logger.traceExit();
            return maini;
        }
    }
}
