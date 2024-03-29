package com.kinpos.dao.hibernate;

import com.kinpos.dao.CashPaymentDAO;
import com.kinpos.models.CashPaymentEntity;
import com.kinpos.models.RunDateTableEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.kinpos.gui.resources.Constants.TILL_ID;

/**
 * Created by kinyua on 5/27/15.
 */
public class HibernateCashPaymentDAO implements CashPaymentDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<CashPaymentEntity> getAllMyCashPayments() {
        Session session=sessionFactory.openSession();
        List<CashPaymentEntity> cashPaymentEntities= session.createCriteria(CashPaymentEntity.class).list();
        session.close();
        return cashPaymentEntities;
    }

    @Override
    public List<CashPaymentEntity> getTillCashPayments() {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CashPaymentEntity.class);
        criteria.add(Restrictions.eq("zedClear", false));
        criteria.add(Restrictions.eq("tillId", TILL_ID));
        List list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public CashPaymentEntity getMyCashPayment(Integer myCashPaymentEntityId) {
        Session session=sessionFactory.openSession();
        CashPaymentEntity cashPaymentEntity=(CashPaymentEntity) session.get(CashPaymentEntity.class, myCashPaymentEntityId);
        session.close();
        return  cashPaymentEntity;
    }

    @Override
    public void purgeMyCashPayment(CashPaymentEntity cashPaymentEntity) {
        sessionFactory.getCurrentSession().delete(cashPaymentEntity);
    }

    @Override
    public CashPaymentEntity saveMyCashPayment(CashPaymentEntity cashPaymentEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(cashPaymentEntity);
        tx.commit();
        session.close();
        return cashPaymentEntity;
    }

    @Override
    public CashPaymentEntity updateMyCashPayment(CashPaymentEntity cashPaymentEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(cashPaymentEntity);
        tx.commit();
        session.close();
        return cashPaymentEntity;
    }
}
