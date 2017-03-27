package com.kinpos.dao.hibernate;

import com.kinpos.dao.AccountDAO;
import com.kinpos.models.AccountEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateAccountDAO implements AccountDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public List<AccountEntity> getAllMyRunDates() {
        Session session =sessionFactory.openSession();
        List<AccountEntity> accountEntities=session.createCriteria(AccountEntity.class).list();
        session.close();
        return accountEntities;
    }

    @Override
    public AccountEntity getMyRunDate(Integer myAccountEntityId) {
        Session session=sessionFactory.openSession();
        AccountEntity accountEntity=(AccountEntity) session.get(AccountEntity.class, myAccountEntityId);
        session.close();
        return accountEntity;
    }

    @Override
    public void purgeMyRunDate(AccountEntity accountEntity) {
        sessionFactory.getCurrentSession().delete(accountEntity);
    }

    @Override
    public AccountEntity saveMyRunDate(AccountEntity accountEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(accountEntity);
        tx.commit();
        session.close();
        return accountEntity;
    }

    @Override
    public AccountEntity updateMyRunDate(AccountEntity accountEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(accountEntity);
        tx.commit();
        session.close();
        return accountEntity;
    }
}
