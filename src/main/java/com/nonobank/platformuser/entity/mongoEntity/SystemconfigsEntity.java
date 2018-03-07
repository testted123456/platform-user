package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class SystemconfigsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "systemconfigs";
    @Id
    private String _id;
    private Date updateTime;
    private Date createTime;
    private String configs;
    private String jiraid;


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

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getConfigs() {
        return configs;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getJiraid() {
        return jiraid;
    }

    public void setJiraid(String jiraid) {
        this.jiraid = jiraid;
    }
}