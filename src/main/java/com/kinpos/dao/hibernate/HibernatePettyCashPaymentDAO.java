package com.kinpos.dao.hibernate;

import com.kinpos.dao.PettyCashPaymentDAO;
import com.kinpos.models.PettyCashPaymentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by kinyua on 5/27/15.
 */
public class HibernatePettyCashPaymentDAO implements PettyCashPaymentDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<PettyCashPaymentEntity> getAllMyPettyCashPayments() {
        Session session=sessionFactory.openSession();
        List<PettyCashPaymentEntity> pettyCashPaymentEntities=session.createCriteria(PettyCashPaymentEntity.class).list();
        session.close();
        return pettyCashPaymentEntities;
    }

    @Override
    public PettyCashPaymentEntity getMyPettyCashPayment(Integer myPettyCashPaymentEntityId) {
        Session session=sessionFactory.openSession();
        PettyCashPaymentEntity pettyCashPaymentEntity=(PettyCashPaymentEntity) session.get(PettyCashPaymentEntity.class, myPettyCashPaymentEntityId);
        session.close();
        return pettyCashPaymentEntity;
    }

    @Override
    public void purgeMyPettyCashPayment(PettyCashPaymentEntity pettyCashPaymentEntity) {
        sessionFactory.getCurrentSession().delete(pettyCashPaymentEntity);
    }

    @Override
    public PettyCashPaymentEntity saveMyPettyCashPayment(PettyCashPaymentEntity pettyCashPaymentEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(pettyCashPaymentEntity);
        tx.commit();
        session.close();
        return pettyCashPaymentEntity;
    }

    @Override
    public PettyCashPaymentEntity updateMyPettyCashPayment(PettyCashPaymentEntity pettyCashPaymentEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(pettyCashPaymentEntity);
        tx.commit();
        session.close();
        return pettyCashPaymentEntity;
    }
}
