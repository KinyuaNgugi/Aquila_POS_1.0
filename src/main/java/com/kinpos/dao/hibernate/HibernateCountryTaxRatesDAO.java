package com.kinpos.dao.hibernate;

import com.kinpos.dao.CountryTaxRatesDAO;
import com.kinpos.dao.ReceiptDAO;
import com.kinpos.models.CountryTaxRatesEntity;
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
public class HibernateCountryTaxRatesDAO implements CountryTaxRatesDAO{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<CountryTaxRatesEntity> getCountryTaxRates() {
        Session session =sessionFactory.openSession();
        List<CountryTaxRatesEntity> accountEntities=session.createCriteria(CountryTaxRatesEntity.class).list();
        session.close();
        return accountEntities;
    }

    @Override
    public CountryTaxRatesEntity getMyTaxRate(Integer tax_rate) {
        Session session=sessionFactory.openSession();
        CountryTaxRatesEntity incomeEntity =(CountryTaxRatesEntity) session.get(CountryTaxRatesEntity.class, tax_rate);
        session.close();
        return incomeEntity;
    }
}
