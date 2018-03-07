package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class RequirementsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "requirements";
    @Id
    private String _id;
    private Date updateTime;
    private String jiraid;
    private String jiraSummary;
    private String requirementType;
    private Date createTime;
    private int testinfoStatus;
    private String qa;
    private String developers;
    private String remark;
    private String mailto;
    private String productLine;
    private String productManager;
    private String projectName;
    private String projectManager;
    private String jiraDesc;
    private int requirementStatus;
    private String testplanid;
    private String emailGroup;
    private Date onlineDate;


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

    public String getJiraid() {
        return jiraid;
    }

    public void setJiraid(String jiraid) {
        this.jiraid = jiraid;
    }

    public String getJirasummary() {
        return jiraSummary;
    }

    public void setJirasummary(String jiraSummary) {
        this.jiraSummary = jiraSummary;
    }

    public String getRequirementtype() {
        return requirementType;
    }

    public void setRequirementtype(String requirementType) {
        this.requirementType = requirementType;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public int getTestinfostatus() {
        return testinfoStatus;
    }

    public void setTestinfostatus(int testinfoStatus) {
        this.testinfoStatus = testinfoStatus;
    }

    public String getQa() {
        return qa;
    }

    public void setQa(String qa) {
        this.qa = qa;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
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

    public String getProductline() {
        return productLine;
    }

    public void setProductline(String productLine) {
        this.productLine = productLine;
    }

    public String getProductmanager() {
        return productManager;
    }

    public void setProductmanager(String productManager) {
        this.productManager = productManager;
    }

    public String getProjectname() {
        return projectName;
    }

    public void setProjectname(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectmanager() {
        return projectManager;
    }

    public void setProjectmanager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getJiradesc() {
        return jiraDesc;
    }

    public void setJiradesc(String jiraDesc) {
        this.jiraDesc = jiraDesc;
    }

    public int getRequirementstatus() {
        return requirementStatus;
    }

    public void setRequirementstatus(int requirementStatus) {
        this.requirementStatus = requirementStatus;
    }

    public String getTestplanid() {
        return testplanid;
    }

    public void setTestplanid(String testplanid) {
        this.testplanid = testplanid;
    }

    public String getEmailgroup() {
        return emailGroup;
    }

    public void setEmailgroup(String emailGroup) {
        this.emailGroup = emailGroup;
    }

    public Date getOnlinedate() {
        return onlineDate;
    }

    public void setOnlinedate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }
}