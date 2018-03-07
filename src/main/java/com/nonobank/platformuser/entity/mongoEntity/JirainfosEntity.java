package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class JirainfosEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "jirainfos";
    @Id
    private String _id;
    private Date updateTime;
    private String jiraid;
    private String jiraDesc;
    private String featureType;
    private String projectManager;
    private String developers;
    private String testers;
    private String onlineDate;
    private String modifyFutures;
    private String sphere;
    private String isUnitTest;
    private String isAddSQL;
    private String addSQLAddress;
    private String isComplexSQL;
    private String complexSQLAddress;
    private String isNewJob;
    private String isNeedST;
    private String STBResult;
    private String STBRemark;
    private String SITResult;
    private String SITRemark;
    private String PREResult;
    private String PRERemark;
    private String PRDResult;
    private String PRDRemark;
    private Date createTime;
    private String newMqTopics;
    private String systemList;
    private int jiraStatus;
    private String mailto;


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

    public String getJiradesc() {
        return jiraDesc;
    }

    public void setJiradesc(String jiraDesc) {
        this.jiraDesc = jiraDesc;
    }

    public String getFeaturetype() {
        return featureType;
    }

    public void setFeaturetype(String featureType) {
        this.featureType = featureType;
    }

    public String getProjectmanager() {
        return projectManager;
    }

    public void setProjectmanager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getTesters() {
        return testers;
    }

    public void setTesters(String testers) {
        this.testers = testers;
    }

    public String getOnlinedate() {
        return onlineDate;
    }

    public void setOnlinedate(String onlineDate) {
        this.onlineDate = onlineDate;
    }

    public String getModifyfutures() {
        return modifyFutures;
    }

    public void setModifyfutures(String modifyFutures) {
        this.modifyFutures = modifyFutures;
    }

    public String getSphere() {
        return sphere;
    }

    public void setSphere(String sphere) {
        this.sphere = sphere;
    }

    public String getIsunittest() {
        return isUnitTest;
    }

    public void setIsunittest(String isUnitTest) {
        this.isUnitTest = isUnitTest;
    }

    public String getIsaddsql() {
        return isAddSQL;
    }

    public void setIsaddsql(String isAddSQL) {
        this.isAddSQL = isAddSQL;
    }

    public String getAddsqladdress() {
        return addSQLAddress;
    }

    public void setAddsqladdress(String addSQLAddress) {
        this.addSQLAddress = addSQLAddress;
    }

    public String getIscomplexsql() {
        return isComplexSQL;
    }

    public void setIscomplexsql(String isComplexSQL) {
        this.isComplexSQL = isComplexSQL;
    }

    public String getComplexsqladdress() {
        return complexSQLAddress;
    }

    public void setComplexsqladdress(String complexSQLAddress) {
        this.complexSQLAddress = complexSQLAddress;
    }

    public String getIsnewjob() {
        return isNewJob;
    }

    public void setIsnewjob(String isNewJob) {
        this.isNewJob = isNewJob;
    }

    public String getIsneedst() {
        return isNeedST;
    }

    public void setIsneedst(String isNeedST) {
        this.isNeedST = isNeedST;
    }

    public String getStbresult() {
        return STBResult;
    }

    public void setStbresult(String STBResult) {
        this.STBResult = STBResult;
    }

    public String getStbremark() {
        return STBRemark;
    }

    public void setStbremark(String STBRemark) {
        this.STBRemark = STBRemark;
    }

    public String getSitresult() {
        return SITResult;
    }

    public void setSitresult(String SITResult) {
        this.SITResult = SITResult;
    }

    public String getSitremark() {
        return SITRemark;
    }

    public void setSitremark(String SITRemark) {
        this.SITRemark = SITRemark;
    }

    public String getPreresult() {
        return PREResult;
    }

    public void setPreresult(String PREResult) {
        this.PREResult = PREResult;
    }

    public String getPreremark() {
        return PRERemark;
    }

    public void setPreremark(String PRERemark) {
        this.PRERemark = PRERemark;
    }

    public String getPrdresult() {
        return PRDResult;
    }

    public void setPrdresult(String PRDResult) {
        this.PRDResult = PRDResult;
    }

    public String getPrdremark() {
        return PRDRemark;
    }

    public void setPrdremark(String PRDRemark) {
        this.PRDRemark = PRDRemark;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNewmqtopics() {
        return newMqTopics;
    }

    public void setNewmqtopics(String newMqTopics) {
        this.newMqTopics = newMqTopics;
    }

    public String getSystemlist() {
        return systemList;
    }

    public void setSystemlist(String systemList) {
        this.systemList = systemList;
    }

    public int getJirastatus() {
        return jiraStatus;
    }

    public void setJirastatus(int jiraStatus) {
        this.jiraStatus = jiraStatus;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }
}