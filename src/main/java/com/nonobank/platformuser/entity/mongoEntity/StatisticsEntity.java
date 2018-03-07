package com.nonobank.platformuser.entity.mongoEntity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class StatisticsEntity {


    private static final long serialVersionUID = -3258839839160856613L;
    public static final String COLLECTION_NAME = "statistics";
    @Id
    private String _id;
    private Date updateTime;
    private String system;
    private Date createTime;
    private int saveEfficiencyForAutoTest;
    private int autoTestRatio;
    private int autoTestBugCount;
    private int manualTestBugCount;
    private int caseManualExcuteTotal;
    private int caseAutoExcuteTotal;
    private int caseTotal;
    private int saveEfficiencyForPackage;
    private int packageAutoCount;
    private String saveEfficiencyForDeploy;
    private int deployAutoCount;
    private String saveEfficiencyForRequestTest;
    private int requestTestCount;
    private String saveEfficiencyForConfig;
    private int configLastApproveCount;
    private int configFirstApproveCount;
    private int branchAssignManualCount;
    private String saveEfficiencyForBranch;
    private int branchAssignAutoCount;


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

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSaveefficiencyforautotest() {
        return saveEfficiencyForAutoTest;
    }

    public void setSaveefficiencyforautotest(int saveEfficiencyForAutoTest) {
        this.saveEfficiencyForAutoTest = saveEfficiencyForAutoTest;
    }

    public int getAutotestratio() {
        return autoTestRatio;
    }

    public void setAutotestratio(int autoTestRatio) {
        this.autoTestRatio = autoTestRatio;
    }

    public int getAutotestbugcount() {
        return autoTestBugCount;
    }

    public void setAutotestbugcount(int autoTestBugCount) {
        this.autoTestBugCount = autoTestBugCount;
    }

    public int getManualtestbugcount() {
        return manualTestBugCount;
    }

    public void setManualtestbugcount(int manualTestBugCount) {
        this.manualTestBugCount = manualTestBugCount;
    }

    public int getCasemanualexcutetotal() {
        return caseManualExcuteTotal;
    }

    public void setCasemanualexcutetotal(int caseManualExcuteTotal) {
        this.caseManualExcuteTotal = caseManualExcuteTotal;
    }

    public int getCaseautoexcutetotal() {
        return caseAutoExcuteTotal;
    }

    public void setCaseautoexcutetotal(int caseAutoExcuteTotal) {
        this.caseAutoExcuteTotal = caseAutoExcuteTotal;
    }

    public int getCasetotal() {
        return caseTotal;
    }

    public void setCasetotal(int caseTotal) {
        this.caseTotal = caseTotal;
    }

    public int getSaveefficiencyforpackage() {
        return saveEfficiencyForPackage;
    }

    public void setSaveefficiencyforpackage(int saveEfficiencyForPackage) {
        this.saveEfficiencyForPackage = saveEfficiencyForPackage;
    }

    public int getPackageautocount() {
        return packageAutoCount;
    }

    public void setPackageautocount(int packageAutoCount) {
        this.packageAutoCount = packageAutoCount;
    }

    public String getSaveefficiencyfordeploy() {
        return saveEfficiencyForDeploy;
    }

    public void setSaveefficiencyfordeploy(String saveEfficiencyForDeploy) {
        this.saveEfficiencyForDeploy = saveEfficiencyForDeploy;
    }

    public int getDeployautocount() {
        return deployAutoCount;
    }

    public void setDeployautocount(int deployAutoCount) {
        this.deployAutoCount = deployAutoCount;
    }

    public String getSaveefficiencyforrequesttest() {
        return saveEfficiencyForRequestTest;
    }

    public void setSaveefficiencyforrequesttest(String saveEfficiencyForRequestTest) {
        this.saveEfficiencyForRequestTest = saveEfficiencyForRequestTest;
    }

    public int getRequesttestcount() {
        return requestTestCount;
    }

    public void setRequesttestcount(int requestTestCount) {
        this.requestTestCount = requestTestCount;
    }

    public String getSaveefficiencyforconfig() {
        return saveEfficiencyForConfig;
    }

    public void setSaveefficiencyforconfig(String saveEfficiencyForConfig) {
        this.saveEfficiencyForConfig = saveEfficiencyForConfig;
    }

    public int getConfiglastapprovecount() {
        return configLastApproveCount;
    }

    public void setConfiglastapprovecount(int configLastApproveCount) {
        this.configLastApproveCount = configLastApproveCount;
    }

    public int getConfigfirstapprovecount() {
        return configFirstApproveCount;
    }

    public void setConfigfirstapprovecount(int configFirstApproveCount) {
        this.configFirstApproveCount = configFirstApproveCount;
    }

    public int getBranchassignmanualcount() {
        return branchAssignManualCount;
    }

    public void setBranchassignmanualcount(int branchAssignManualCount) {
        this.branchAssignManualCount = branchAssignManualCount;
    }

    public String getSaveefficiencyforbranch() {
        return saveEfficiencyForBranch;
    }

    public void setSaveefficiencyforbranch(String saveEfficiencyForBranch) {
        this.saveEfficiencyForBranch = saveEfficiencyForBranch;
    }

    public int getBranchassignautocount() {
        return branchAssignAutoCount;
    }

    public void setBranchassignautocount(int branchAssignAutoCount) {
        this.branchAssignAutoCount = branchAssignAutoCount;
    }
}