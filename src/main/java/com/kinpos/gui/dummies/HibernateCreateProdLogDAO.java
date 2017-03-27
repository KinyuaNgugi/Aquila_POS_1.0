package com.kinpos.gui.dummies;

import com.kinpos.models.CreateProdLogEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateCreateProdLogDAO implements CreateProdLogDAO {
    private SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    @Override
    public List<CreateProdLogEntity> getAllMyRunDates() {
        Session session =sessionFactory.openSession();
        List<CreateProdLogEntity> createProdLogEntities=session.createCriteria(CreateProdLogEntity.class).list();
        session.close();
        return createProdLogEntities;
    }

    @Override
    public CreateProdLogEntity getMyRunDate(Integer myCreateProdLogEntityId) {
        Session session=sessionFactory.openSession();
        CreateProdLogEntity createProdLogEntity=(CreateProdLogEntity) session.get(CreateProdLogEntity.class, myCreateProdLogEntityId);
        session.close();
        return createProdLogEntity;
    }

    @Override
    public void purgeMyRunDate(CreateProdLogEntity createProdLogEntity) {
        sessionFactory.getCurrentSession().delete(createProdLogEntity);
    }

    @Override
    public CreateProdLogEntity saveMyRunDate(CreateProdLogEntity createProdLogEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(createProdLogEntity);
        tx.commit();
        session.close();
        return createProdLogEntity;
    }

    @Override
    public CreateProdLogEntity updateMyRunDate(CreateProdLogEntity createProdLogEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(createProdLogEntity);
        tx.commit();
        session.close();
        return createProdLogEntity;
    }
}
