package com.kinpos.dao.hibernate;

import com.kinpos.dao.ReceiptDAO;
import com.kinpos.models.IncomeEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateReceiptDAO implements ReceiptDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<IncomeEntity> getAllMyReceipts() {
        Session session =sessionFactory.openSession();
        List<IncomeEntity> accountEntities=session.createCriteria(IncomeEntity.class).list();
        session.close();
        return accountEntities;
    }

    @Override
    public IncomeEntity getMyReceipt(Integer myReceiptEntityId) {
        Session session=sessionFactory.openSession();
        IncomeEntity incomeEntity =(IncomeEntity) session.get(IncomeEntity.class, myReceiptEntityId);
        session.close();
        return incomeEntity;
    }

    @Override
    public void purgeMyReceipt(IncomeEntity incomeEntity) {
        sessionFactory.getCurrentSession().delete(incomeEntity);
    }

    @Override
    public IncomeEntity saveMyReceipt(IncomeEntity incomeEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(incomeEntity);
        tx.commit();
        session.close();
        return incomeEntity;
    }

    @Override
    public IncomeEntity updateMyReceipt(IncomeEntity incomeEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(incomeEntity);
        tx.commit();
        session.close();
        return incomeEntity;
    }

    @Override
    public List<IncomeEntity> getAllMyUnclearedReceipts() {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(IncomeEntity.class);
        criteria.add(Restrictions.eq("zedClear", false));
        List list = criteria.list();
        session.close();
        return list;
    }

}
