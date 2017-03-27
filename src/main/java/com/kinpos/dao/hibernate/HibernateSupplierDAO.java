package com.kinpos.dao.hibernate;

import com.kinpos.dao.SupplierDAO;
import com.kinpos.models.SupplierEntity;
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
public class HibernateSupplierDAO implements SupplierDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    Session session = sessionFactory.openSession();

    Transaction transaction = session.beginTransaction();

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<SupplierEntity> getAllMySuppliers() {
        Session session=sessionFactory.openSession();
        List<SupplierEntity>supplierEntities=session.createCriteria(SupplierEntity.class).list();
        session.close();
        return supplierEntities;
    }

    @Override
    public SupplierEntity getMySupplier(Integer mySupplierEntityId) {
        Session session=sessionFactory.openSession();
        SupplierEntity supplierEntity=(SupplierEntity) session.get(SupplierEntity.class, mySupplierEntityId);
        session.close();
        return supplierEntity;
    }

    @Override
    public void purgeMySupplier(SupplierEntity supplierEntity)
    {
        sessionFactory.getCurrentSession().delete(supplierEntity);
    }

    @Override
    public SupplierEntity saveMySupplier(SupplierEntity supplierEntity) {
        sessionFactory.openSession().save(supplierEntity);
        return supplierEntity;
    }

    @Override
    public SupplierEntity updateMySupplier(SupplierEntity supplierEntity) {
        sessionFactory.getCurrentSession().update(supplierEntity);
        return supplierEntity;
    }
    @Override
    public List<SupplierEntity> getMySupplierBySupplierName(String supName) {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SupplierEntity.class, supName);
        criteria.add(Restrictions.eq("supplierName", supName));
        List list = criteria.list();
        session.close();
        return list;
    }
}
