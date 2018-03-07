package com.nonobank.platformuser.entity.mongoEntity;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class ConfigsEntity extends BaseEntity{


    private static final long serialVersionUID = -3258839839160856613L;
    public static String COLLECTION_NAME = "configs";
    @Id
    private String _id;
    private Date updateTime;
    private String key;
    private String value;
    private Date createTime;
    private String changeList;


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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChangelist() {
        return changeList;
    }

    public void setChangelist(String changeList) {
        this.changeList = changeList;
    }


    public static void main(String[] args) {
        BaseEntity configsEntity = new ConfigsEntity();
        System.out.println(configsEntity.getCollectionName());

    }
}