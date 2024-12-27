// src/main/java/com/example/permissionsystem/service/OrganizationTreeNode.java
package com.example.permissionsystem.service;

import com.example.permissionsystem.model.TOrganization;

import java.util.ArrayList;
import java.util.List;

public class OrganizationTreeNode {
    private String fID;
    private String fName;
    private String fHigherUpfIDs;
    private String fPermissions;
    private String fRemark;
    private String fOrgGUID;
    private List<OrganizationTreeNode> children;

    public OrganizationTreeNode(TOrganization organization) {
        this.fID = organization.getFID();
        this.fName = organization.getFName();
        this.fHigherUpfIDs = organization.getFHigherUpfIDs();
        this.fPermissions = organization.getFPermissions();
        this.fRemark = organization.getFRemark();
        this.fOrgGUID = organization.getFOrgGUID();
        this.children = new ArrayList<>();
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

    public List<OrganizationTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationTreeNode> children) {
        this.children = children;
    }

    public void addChild(OrganizationTreeNode child) {
        this.children.add(child);
    }
}
