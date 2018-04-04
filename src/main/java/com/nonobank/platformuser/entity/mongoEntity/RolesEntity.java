package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class RolesEntity extends BaseEntity{


    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    private String _id;
    private Date updateTime;
    private String rolename;
    private String desc;
    private Date createTime;
    private String permissions;
    private String menus;
    private boolean status;
    private String role;


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

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}