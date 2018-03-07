package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class SystembranchesEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "systembranches";
    @Id
    private String _id;
    private Date updateTime;
    private String testenv;
    private String system;
    private Date createTime;
    private String jirainfo;
    private String branch;


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

    public String getTestenv() {
        return testenv;
    }

    public void setTestenv(String testenv) {
        this.testenv = testenv;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getJirainfo() {
        return jirainfo;
    }

    public void setJirainfo(String jirainfo) {
        this.jirainfo = jirainfo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}