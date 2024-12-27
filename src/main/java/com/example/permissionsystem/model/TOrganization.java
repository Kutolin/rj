// src/main/java/com/example/permissionsystem/model/TOrganization.java
package com.example.permissionsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_organization")
public class TOrganization {

    @Id
    private String fID;
    private String fName;
    private String fHigherUpfIDs;
    private String fPermissions;
    private String fRemark;
    private String fOrgGUID;

    // Constructors
    public TOrganization() {}

    public TOrganization(String fID, String fName, String fHigherUpfIDs, String fPermissions, String fRemark, String fOrgGUID) {
        this.fID = fID;
        this.fName = fName;
        this.fHigherUpfIDs = fHigherUpfIDs;
        this.fPermissions = fPermissions;
        this.fRemark = fRemark;
        this.fOrgGUID = fOrgGUID;
    }

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
