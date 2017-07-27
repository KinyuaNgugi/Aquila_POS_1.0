package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by openworldkin on 5/16/17.
 */
@Entity
@Table(name = "org_chart", schema = "pos2", catalog = "")
public class OrgChartEntity {
    private int id;
    private Integer orgId;
    private Integer number;
    private Integer mainAccId;
    private Integer levelOneId;
    private Integer levelTwoId;
    private String levelThree;
    private String currency;
    private String type;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "org_id")
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "main_acc_id")
    public Integer getMainAccId() {
        return mainAccId;
    }

    public void setMainAccId(Integer mainAccId) {
        this.mainAccId = mainAccId;
    }

    @Basic
    @Column(name = "level_one_id")
    public Integer getLevelOneId() {
        return levelOneId;
    }

    public void setLevelOneId(Integer levelOneId) {
        this.levelOneId = levelOneId;
    }

    @Basic
    @Column(name = "level_two_id")
    public Integer getLevelTwoId() {
        return levelTwoId;
    }

    public void setLevelTwoId(Integer levelTwoId) {
        this.levelTwoId = levelTwoId;
    }

    @Basic
    @Column(name = "level_three")
    public String getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(String levelThree) {
        this.levelThree = levelThree;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgChartEntity that = (OrgChartEntity) o;

        if (id != that.id) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (mainAccId != null ? !mainAccId.equals(that.mainAccId) : that.mainAccId != null) return false;
        if (levelOneId != null ? !levelOneId.equals(that.levelOneId) : that.levelOneId != null) return false;
        if (levelTwoId != null ? !levelTwoId.equals(that.levelTwoId) : that.levelTwoId != null) return false;
        if (levelThree != null ? !levelThree.equals(that.levelThree) : that.levelThree != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (mainAccId != null ? mainAccId.hashCode() : 0);
        result = 31 * result + (levelOneId != null ? levelOneId.hashCode() : 0);
        result = 31 * result + (levelTwoId != null ? levelTwoId.hashCode() : 0);
        result = 31 * result + (levelThree != null ? levelThree.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
