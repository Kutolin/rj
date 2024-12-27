// src/main/java/com/example/permissionsystem/dto/UpdateOrganizationRequest.java
package com.example.permissionsystem.dto;

public class UpdateOrganizationRequest {
    private String fID;
    private String fName;
    private String fHigherUpfIDs;
    private String fPermissions;
    private String fRemark;
    private String fOrgGUID;

    // Getters and Setters
    public String getFID() {
        return fID;
    }

    public void setFID(String fID) {
        this.fID = fID;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFHigherUpfIDs() {
        return fHigherUpfIDs;
    }

    public void setFHigherUpfIDs(String fHigherUpfIDs) {
        this.fHigherUpfIDs = fHigherUpfIDs;
    }

    public String getFPermissions() {
        return fPermissions;
    }

    public void setFPermissions(String fPermissions) {
        this.fPermissions = fPermissions;
    }

    public String getFRemark() {
        return fRemark;
    }

    public void setFRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public String getFOrgGUID() {
        return fOrgGUID;
    }

    public void setFOrgGUID(String fOrgGUID) {
        this.fOrgGUID = fOrgGUID;
    }
}

