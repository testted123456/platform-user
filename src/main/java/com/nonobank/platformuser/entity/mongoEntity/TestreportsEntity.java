package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class TestreportsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "testreports";
    @Id
    private String _id;
    private String jirainfo;
    private Date createTime;
    private Date updateTime;
    private String tittle;
    private String risk;
    private String scope;
    private String keypoint;
    private String progress_summary;
    private String progress_casedetail;
    private String bug_summary;
    private String bug_list;
    private String testcase_summary;
    private String testcase_list;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJirainfo() {
        return jirainfo;
    }

    public void setJirainfo(String jirainfo) {
        this.jirainfo = jirainfo;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updateTime;
    }

    public void setUpdatetime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getKeypoint() {
        return keypoint;
    }

    public void setKeypoint(String keypoint) {
        this.keypoint = keypoint;
    }

    public String getProgress_summary() {
        return progress_summary;
    }

    public void setProgress_summary(String progress_summary) {
        this.progress_summary = progress_summary;
    }

    public String getProgress_casedetail() {
        return progress_casedetail;
    }

    public void setProgress_casedetail(String progress_casedetail) {
        this.progress_casedetail = progress_casedetail;
    }

    public String getBug_summary() {
        return bug_summary;
    }

    public void setBug_summary(String bug_summary) {
        this.bug_summary = bug_summary;
    }

    public String getBug_list() {
        return bug_list;
    }

    public void setBug_list(String bug_list) {
        this.bug_list = bug_list;
    }

    public String getTestcase_summary() {
        return testcase_summary;
    }

    public void setTestcase_summary(String testcase_summary) {
        this.testcase_summary = testcase_summary;
    }

    public String getTestcase_list() {
        return testcase_list;
    }

    public void setTestcase_list(String testcase_list) {
        this.testcase_list = testcase_list;
    }
}