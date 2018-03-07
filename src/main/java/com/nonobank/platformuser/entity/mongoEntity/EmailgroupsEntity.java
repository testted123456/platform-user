package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class EmailgroupsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "emailgroups";
    @Id
    private String _id;
    private Date updateTime;
    private String egName;
    private Date createTime;
    private String remark;
    private String mailto;


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

    public String getEgname() {
        return egName;
    }

    public void setEgname(String egName) {
        this.egName = egName;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }
}