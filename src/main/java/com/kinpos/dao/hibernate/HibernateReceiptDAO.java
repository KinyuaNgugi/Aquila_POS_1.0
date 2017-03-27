package com.kinpos.dao.hibernate;

import com.kinpos.dao.ReceiptDAO;
import com.kinpos.models.ReceiptEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateReceiptDAO implements ReceiptDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<ReceiptEntity> getAllMyReceipts() {
        Session session =sessionFactory.openSession();
        List<ReceiptEntity> accountEntities=session.createCriteria(ReceiptEntity.class).list();
        session.close();
        return accountEntities;
    }

    @Override
    public ReceiptEntity getMyReceipt(Integer myReceiptEntityId) {
        Session session=sessionFactory.openSession();
        ReceiptEntity receiptEntity=(ReceiptEntity) session.get(ReceiptEntity.class, myReceiptEntityId);
        session.close();
        return receiptEntity;
    }

    @Override
    public void purgeMyReceipt(ReceiptEntity receiptEntity) {
        sessionFactory.getCurrentSession().delete(receiptEntity);
    }

    @Override
    public ReceiptEntity saveMyReceipt(ReceiptEntity receiptEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(receiptEntity);
        tx.commit();
        session.close();
        return receiptEntity;
    }

    @Override
    public ReceiptEntity updateMyReceipt(ReceiptEntity receiptEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(receiptEntity);
        tx.commit();
        session.close();
        return receiptEntity;
    }

}
