package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class UsersEntity extends BaseEntity{


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "users";
    @Id
    private String _id;
    private String username;
    private String nickname;
    private Date date;
    private int __v;
    private String email;
    private String password;
    private String validStatus;
    private String organization;
    private List<RolesEntity> roles;
    private String department;
    private String departmentSecond;
    private String departmentThird;
    private String departmentFourth;
    private boolean isLogin;

    private int permission;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<RolesEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesEntity> roles) {
        this.roles = roles;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentSecond() {
        return departmentSecond;
    }

    public void setDepartmentSecond(String departmentSecond) {
        this.departmentSecond = departmentSecond;
    }

    public String getDepartmentThird() {
        return departmentThird;
    }

    public void setDepartmentThird(String departmentThird) {
        this.departmentThird = departmentThird;
    }

    public String getDepartmentFourth() {
        return departmentFourth;
    }

    public void setDepartmentFourth(String departmentFourth) {
        this.departmentFourth = departmentFourth;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}