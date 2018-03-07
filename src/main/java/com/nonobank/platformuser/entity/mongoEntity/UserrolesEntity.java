package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class UserrolesEntity extends BaseEntity{


    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    private String _id;
    private Date updateTime;
    private ObjectId user;
    private ObjectId role;
    private Date createTime;


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

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public ObjectId getRole() {
        return role;
    }

    public void setRole(ObjectId role) {
        this.role = role;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }
}