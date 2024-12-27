// src/main/java/com/example/permissionsystem/dto/CreateUserRequest.java
package com.example.permissionsystem.dto;

public class CreateUserRequest {
    private int fID;
    private String fOrgIDs;
    private String fUserGUID;
    private String fName;
    private String fPassword;
    private String fRemark;

    // Getters and Setters
    public int getFID() {
        return fID;
    }

    public void setFID(int fID) {
        this.fID = fID;
    }

    public String getFOrgIDs() {
        return fOrgIDs;
    }

    public void setFOrgIDs(String fOrgIDs) {
        this.fOrgIDs = fOrgIDs;
    }

    public String getFUserGUID() {
        return fUserGUID;
    }

    public void setFUserGUID(String fUserGUID) {
        this.fUserGUID = fUserGUID;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFPassword() {
        return fPassword;
    }

    public void setFPassword(String fPassword) {
        this.fPassword = fPassword;
    }

    public String getFRemark() {
        return fRemark;
    }

    public void setFRemark(String fRemark) {
        this.fRemark = fRemark;
    }
}

