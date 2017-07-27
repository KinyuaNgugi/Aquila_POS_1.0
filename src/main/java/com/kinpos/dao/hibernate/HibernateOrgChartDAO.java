package com.kinpos.dao.hibernate;

import com.kinpos.dao.OrgChartDAO;
import com.kinpos.dao.ReceiptDAO;
import com.kinpos.models.IncomeEntity;
import com.kinpos.models.OrgChartEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateOrgChartDAO implements OrgChartDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<OrgChartEntity> getMyAccountByNumber(Integer reference)
    {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(OrgChartEntity.class);
        criteria.add(Restrictions.eq("number", reference));
        List list = criteria.list();
        session.close();
        return list;
    }

}
