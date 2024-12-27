// src/main/java/com/example/permissionsystem/manager/OrganizationUserManager.java
package com.example.permissionsystem.manager;

import com.example.permissionsystem.dao.TOrganizationDAO;
import com.example.permissionsystem.dao.TUserDAO;
import com.example.permissionsystem.factory.DAOFactory;
import com.example.permissionsystem.model.TOrganization;
import com.example.permissionsystem.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationUserManager {

    private final TOrganizationDAO organizationDAO;
    private final TUserDAO userDAO;

    @Autowired
    public OrganizationUserManager(DAOFactory daoFactory) {
        this.organizationDAO = daoFactory.getTOrganizationDAO();
        this.userDAO = daoFactory.getTUserDAO();
    }

    /**
     * 增加组织和用户
     * 同时添加组织和用户，保证事务的一致性
     *
     * @param organization TOrganization实体
     * @param user         TUser实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean addOrganizationAndUser(TOrganization organization, TUser user) {
        boolean orgAdded = organizationDAO.add(organization);
        boolean userAdded = false;
        if (orgAdded) {
            userAdded = userDAO.add(user);
        }
        return orgAdded && userAdded;
    }

    /**
     * 删除组织和关联用户
     * 删除组织，并删除所有属于该组织的用户
     *
     * @param fID 组织ID
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean deleteOrganizationAndUsers(String fID) {
        // 查询所有用户，其fOrgIDs包含fID
        List<TUser> users = userDAO.queryModelList("fOrgIDs LIKE '%|" + fID + "|%'");
        boolean allUsersDeleted = true;
        if (users != null) {
            for (TUser user : users) {
                boolean deleted = userDAO.delete(user.getFID());
                if (!deleted) {
                    allUsersDeleted = false;
                }
            }
        }

        // 删除组织
        boolean orgDeleted = organizationDAO.delete(fID);

        return allUsersDeleted && orgDeleted;
    }
}
