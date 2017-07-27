package com.kinpos.dao.hibernate;

import com.kinpos.dao.SaleDAO;
import com.kinpos.models.IncomeItemsEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.Date;
import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateSaleDAO implements SaleDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<IncomeItemsEntity> getAllMySales() {
        Session session=sessionFactory.openSession();
        List<IncomeItemsEntity> saleEntities=session.createCriteria(IncomeItemsEntity.class).list();
        session.close();
        return saleEntities;
    }

    @Override
    public IncomeItemsEntity getMySale(Integer mySaleEntityId) {
        Session session=sessionFactory.openSession();
        IncomeItemsEntity incomeItemsEntity =(IncomeItemsEntity) session.get(IncomeItemsEntity.class, mySaleEntityId);
        session.close();
        return incomeItemsEntity;
    }

    @Override
    public void purgeMySale(IncomeItemsEntity incomeItemsEntity) {
        sessionFactory.getCurrentSession().delete(incomeItemsEntity);
    }

    @Override
    public IncomeItemsEntity saveMySale(IncomeItemsEntity incomeItemsEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(incomeItemsEntity);
        tx.commit();
        session.close();
        return incomeItemsEntity;
    }

    @Override
    public IncomeItemsEntity updateMySale(IncomeItemsEntity incomeItemsEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(incomeItemsEntity);
        tx.commit();
        session.close();
        return incomeItemsEntity;
    }
    @Override
    public List<IncomeItemsEntity> getCreditSalesByReceipt(String receipt, Date runDate) {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(IncomeItemsEntity.class);
        criteria.add(Restrictions.eq("receiptNumber", Integer.parseInt(receipt)));
        criteria.add(Restrictions.eq("runDate", runDate));
        List list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public List<IncomeItemsEntity> getAllMyUnclearedSales() {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(IncomeItemsEntity.class);
        criteria.add(Restrictions.eq("zedClear", false));
        List list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public List<IncomeItemsEntity> getItemsForReceipt (Integer receipt_id)
    {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(IncomeItemsEntity.class);
        criteria.add(Restrictions.eq("receipt_id", receipt_id));
        List list = criteria.list();
        session.close();
        return list;
    }
}
