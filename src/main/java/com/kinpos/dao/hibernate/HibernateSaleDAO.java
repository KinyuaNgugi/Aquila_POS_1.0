package com.kinpos.dao.hibernate;

import com.kinpos.dao.SaleDAO;
import com.kinpos.models.SaleEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.sql.Date;
import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateSaleDAO implements SaleDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<SaleEntity> getAllMySales() {
        Session session=sessionFactory.openSession();
        List<SaleEntity> saleEntities=session.createCriteria(SaleEntity.class).list();
        session.close();
        return saleEntities;
    }

    @Override
    public SaleEntity getMySale(Integer mySaleEntityId) {
        Session session=sessionFactory.openSession();
        SaleEntity saleEntity=(SaleEntity) session.get(SaleEntity.class, mySaleEntityId);
        session.close();
        return saleEntity;
    }

    @Override
    public void purgeMySale(SaleEntity saleEntity) {
        sessionFactory.getCurrentSession().delete(saleEntity);
    }

    @Override
    public SaleEntity saveMySale(SaleEntity saleEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(saleEntity);
        tx.commit();
        session.close();
        return saleEntity;
    }

    @Override
    public SaleEntity updateMySale(SaleEntity saleEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(saleEntity);
        tx.commit();
        session.close();
        return saleEntity;
    }
    @Override
    public List<SaleEntity> getCreditSalesByReceipt(String receipt, Date runDate) {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SaleEntity.class);
        criteria.add(Restrictions.eq("receiptNumber", Integer.parseInt(receipt)));
        criteria.add(Restrictions.eq("runDate", runDate));
        List list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public List<SaleEntity> getAllMyUnclearedSales() {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SaleEntity.class);
        criteria.add(Restrictions.eq("zedClear", false));
        List list = criteria.list();
        session.close();
        return list;
    }
}
