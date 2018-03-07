package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class TestplansEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "testplans";
    @Id
    private String _id;
    private Date updateTime;
    private String jiraid;
    private Date createTime;
    private String plans;
    private Date devStartDate;
    private Date devEndDate;
    private Date testEndDate;
    private Date testStartDate;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getUpdatetime() {
        return updateTime;
    }

    public void setUpdatetime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJiraid() {
        return jiraid;
    }

    public void setJiraid(String jiraid) {
        this.jiraid = jiraid;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPlans() {
        return plans;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public Date getDevstartdate() {
        return devStartDate;
    }

    public void setDevstartdate(Date devStartDate) {
        this.devStartDate = devStartDate;
    }

    public Date getDevenddate() {
        return devEndDate;
    }

    public void setDevenddate(Date devEndDate) {
        this.devEndDate = devEndDate;
    }

    public Date getTestenddate() {
        return testEndDate;
    }

    public void setTestenddate(Date testEndDate) {
        this.testEndDate = testEndDate;
    }

    public Date getTeststartdate() {
        return testStartDate;
    }

    public void setTeststartdate(Date testStartDate) {
        this.testStartDate = testStartDate;
    }
}