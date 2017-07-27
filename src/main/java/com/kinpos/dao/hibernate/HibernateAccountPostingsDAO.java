package com.kinpos.dao.hibernate;

import com.kinpos.dao.AccountPostingsDAO;
import com.kinpos.dao.RunDateDAO;
import com.kinpos.models.AccountsPostingsEntity;
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
public class HibernateAccountPostingsDAO implements AccountPostingsDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public AccountsPostingsEntity saveMyPosting(AccountsPostingsEntity accountsPostingsEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(accountsPostingsEntity);
        tx.commit();
        session.close();
        return accountsPostingsEntity;
    }

}
