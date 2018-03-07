package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class SystemsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "systems";
    @Id
    private String _id;
    private Date updateTime;
    private String system;
    private String desc;
    private Date createTime;
    private String gitUrl;


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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
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

    public String getGiturl() {
        return gitUrl;
    }

    public void setGiturl(String gitUrl) {
        this.gitUrl = gitUrl;
    }
}