package com.kinpos.dao;

import com.kinpos.models.IncomeEntity;
import com.kinpos.models.OrgChartEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface OrgChartDAO {

    List<OrgChartEntity> getMyAccountByNumber(Integer reference);

}
