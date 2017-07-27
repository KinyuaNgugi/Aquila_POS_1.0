package com.kinpos.dao;

import com.kinpos.models.CashPaymentEntity;
import com.kinpos.models.CountryTaxRatesEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface CountryTaxRatesDAO {

    List<CountryTaxRatesEntity> getCountryTaxRates();

    CountryTaxRatesEntity getMyTaxRate(Integer tax_rate);

}
