package com.kinpos.dao.hibernate;

import com.kinpos.dao.StockDAO;
import com.kinpos.models.StockEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateStockDAO implements StockDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<StockEntity> getAllMyStocks() {
        Session session=sessionFactory.openSession();
        List<StockEntity> stockEntityList=sessionFactory.openSession().createCriteria(StockEntity.class).list();
        session.close();
        return stockEntityList;
    }

    @Override
    public StockEntity getMyStock(Integer myStockEntityId) {
        Session session=sessionFactory.openSession();
        StockEntity stockEntity=(StockEntity) session.get(StockEntity.class, myStockEntityId);
        session.close();
        return stockEntity;
    }

    @Override
    public void purgeMyStock(StockEntity stockEntity) {
        sessionFactory.getCurrentSession().delete(stockEntity);
    }

    @Override
    public StockEntity saveMyStock(StockEntity stockEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(stockEntity);
        tx.commit();
        session.close();
        return stockEntity;
    }

    @Override
    public StockEntity updateMyStock(StockEntity stockEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(stockEntity);
        tx.commit();
        session.close();
        return stockEntity;
    }

    @Override
    public List<StockEntity> getMyStockByProductCode(String productCode) {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(StockEntity.class, productCode);
        criteria.add(Restrictions.eq("productCode", productCode));
        List list = criteria.list();
        session.close();
        return list;
    }
}
