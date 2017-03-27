package com.kinpos.dao.hibernate;

import com.kinpos.dao.StockEdenDAO;
import com.kinpos.models.StockedenEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateStockEdenDAO implements StockEdenDAO {
    private SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    @Override
    public List<StockedenEntity> getAllMyRunDates() {
        Session session =sessionFactory.openSession();
        List<StockedenEntity> accountEntities=session.createCriteria(StockedenEntity.class).list();
        session.close();
        return accountEntities;
    }

    @Override
    public StockedenEntity getMyRunDate(Integer myStockedenEntityId) {
        Session session=sessionFactory.openSession();
        StockedenEntity stockedenEntity=(StockedenEntity) session.get(StockedenEntity.class, myStockedenEntityId);
        session.close();
        return stockedenEntity;
    }

    @Override
    public void purgeMyRunDate(StockedenEntity stockedenEntity) {
        sessionFactory.getCurrentSession().delete(stockedenEntity);
    }

    @Override
    public StockedenEntity saveMyRunDate(StockedenEntity stockedenEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(stockedenEntity);
        tx.commit();
        session.close();
        return stockedenEntity;
    }

    @Override
    public StockedenEntity updateMyRunDate(StockedenEntity stockedenEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(stockedenEntity);
        tx.commit();
        session.close();
        return stockedenEntity;
    }
}
