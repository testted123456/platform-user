package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class UserworklogsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "userworklogs";
    @Id
    private String _id;
    private Date updateTime;
    private String nickname;
    private String email;
    private String user;
    private String username;
    private String departmentThird;
    private String departmentSecond;
    private String department;
    private String organization;
    private Date createTime;
    private String logDetail;
    private Date logDate;


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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartmentthird() {
        return departmentThird;
    }

    public void setDepartmentthird(String departmentThird) {
        this.departmentThird = departmentThird;
    }

    public String getDepartmentsecond() {
        return departmentSecond;
    }

    public void setDepartmentsecond(String departmentSecond) {
        this.departmentSecond = departmentSecond;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLogdetail() {
        return logDetail;
    }

    public void setLogdetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public Date getLogdate() {
        return logDate;
    }

    public void setLogdate(Date logDate) {
        this.logDate = logDate;
    }
}