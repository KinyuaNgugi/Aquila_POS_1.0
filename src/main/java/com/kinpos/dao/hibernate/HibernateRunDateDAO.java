package com.kinpos.dao.hibernate;

import com.kinpos.dao.RunDateDAO;
import com.kinpos.models.RunDateTableEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.kinpos.gui.resources.Constants.TILL_ID;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateRunDateDAO implements RunDateDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<RunDateTableEntity> getAllMyRunDates() {
        Session session=sessionFactory.openSession();
        List<RunDateTableEntity> runDateTableEntities=session.createCriteria(RunDateTableEntity.class).list();
        session.close();
        return runDateTableEntities;
    }

    @Override
    public List<RunDateTableEntity> getMyActiveRunDate()
    {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RunDateTableEntity.class);
        criteria.add(Restrictions.eq("activeStatus", true));
        criteria.add(Restrictions.eq("tillId", TILL_ID));
        List list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public RunDateTableEntity getMyRunDate(Integer myRunDateTableEntityId) {
        Session session=sessionFactory.openSession();
        RunDateTableEntity runDateTableEntity=(RunDateTableEntity) session.get(RunDateTableEntity.class, myRunDateTableEntityId);
        session.close();
        return runDateTableEntity;
    }

    @Override
    public void purgeMyRunDate(RunDateTableEntity runDateTableEntity) {
        sessionFactory.getCurrentSession().delete(runDateTableEntity);
    }

    @Override
    public RunDateTableEntity saveMyRunDate(RunDateTableEntity runDateTableEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(runDateTableEntity);
        tx.commit();
        session.close();
        return runDateTableEntity;
    }

    @Override
    public RunDateTableEntity updateMyRunDate(RunDateTableEntity runDateTableEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(runDateTableEntity);
        tx.commit();
        session.close();
        return runDateTableEntity;
    }
}
