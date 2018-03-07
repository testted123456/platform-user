package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class RolepermissionsEntity extends BaseEntity{


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "rolepermissions";
    @Id
    private String _id;
    private Date updateTime;
    private ObjectId permission;
    private ObjectId role;
    private Date createTime;
    private int rights;


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

    public ObjectId getPermission() {
        return permission;
    }

    public void setPermission(ObjectId permission) {
        this.permission = permission;
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

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }
}