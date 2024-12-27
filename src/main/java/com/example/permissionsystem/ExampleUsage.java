// src/main/java/com/example/permissionsystem/ExampleUsage.java
package com.example.permissionsystem;

import com.example.permissionsystem.dao.TOrganizationDAO;
import com.example.permissionsystem.dao.TUserDAO;
import com.example.permissionsystem.factory.DAOFactory;
import com.example.permissionsystem.manager.OrganizationUserManager;
import com.example.permissionsystem.model.TOrganization;
import com.example.permissionsystem.model.TUser;
import com.example.permissionsystem.service.OrganizationService;
import com.example.permissionsystem.service.OrganizationTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ExampleUsage implements CommandLineRunner {

    private final OrganizationService organizationService;

    @Autowired
    public ExampleUsage(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 添加一个组织
        TOrganization newOrg = new TOrganization(
                "010102",                  // fID
                "开发部",                   // fName
                "0101",                    // fHigherUpfIDs
                "4030|8560",               // fPermissions
                "负责软件开发",              // fRemark
                "GUID-010102"              // fOrgGUID
        );
        boolean addOrgSuccess = organizationService.addOrganization(newOrg);
        System.out.println("添加组织成功: " + addOrgSuccess);

        // 2. 更新组织
        newOrg.setFRemark("负责软件开发与维护");
        boolean updateOrgSuccess = organizationService.updateOrganization(newOrg);
        System.out.println("更新组织成功: " + updateOrgSuccess);

        // 3. 查询组织
        List<TOrganization> organizations = organizationService.queryOrganizations("fID = '010102'");
        System.out.println("查询到的组织:");
        for (TOrganization org : organizations) {
            System.out.println(org);
        }

        // 4. 删除组织
        boolean deleteOrgSuccess = organizationService.deleteOrganization("010102");
        System.out.println("删除组织成功: " + deleteOrgSuccess);

        // 5. 添加一个用户
        TUser newUser = new TUser(
                103,                        // fID
                "|01010303|010301|",         // fOrgIDs
                "e8b601c0-44f4-44bc-9cfe-c682a68eb3cd", // fUserGUID
                "202138130118",             // fName
                "e10adc3949ba59abbe56e057f20f883e", // fPassword (MD5加密示例)
                "22 软工 R1"                 // fRemark
        );
        boolean addUserSuccess = organizationService.addUser(newUser);
        System.out.println("添加用户成功: " + addUserSuccess);

        // 6. 更新用户
        newUser.setFRemark("22 软工 R2");
        boolean updateUserSuccess = organizationService.updateUser(newUser);
        System.out.println("更新用户成功: " + updateUserSuccess);

        // 7. 查询用户
        List<TUser> users = organizationService.queryUsers("fID = 103");
        System.out.println("查询到的用户:");
        for (TUser user : users) {
            System.out.println(user);
        }

        // 8. 删除用户
        boolean deleteUserSuccess = organizationService.deleteUser(103);
        System.out.println("删除用户成功: " + deleteUserSuccess);

        // 9. 获取用户权限
        Set<String> permissions = organizationService.getUserPermissions(103);
        System.out.println("用户103的权限: " + permissions);
    }
}
