// src/main/java/com/example/permissionsystem/service/OrganizationService.java
package com.example.permissionsystem.service;

import com.example.permissionsystem.dao.TOrganizationDAO;
import com.example.permissionsystem.dao.TUserDAO;
import com.example.permissionsystem.model.TOrganization;
import com.example.permissionsystem.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrganizationService {

    private final TOrganizationDAO organizationDAO;
    private final TUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OrganizationService(TOrganizationDAO organizationDAO, TUserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.organizationDAO = organizationDAO;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 添加组织
     *
     * @param organization TOrganization实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean addOrganization(TOrganization organization) {
        return organizationDAO.add(organization);
    }

    /**
     * 更新组织
     *
     * @param organization TOrganization实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean updateOrganization(TOrganization organization) {
        return organizationDAO.update(organization);
    }

    /**
     * 删除组织及其关联用户
     *
     * @param fID 组织ID
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean deleteOrganization(String fID) {
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

    /**
     * 查询组织
     *
     * @param whereClause SQL WHERE 子句
     * @return 组织列表
     */
    public List<TOrganization> queryOrganizations(String whereClause) {
        return organizationDAO.queryModelList(whereClause);
    }

    /**
     * 添加用户
     *
     * @param user TUser实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean addUser(TUser user) {
        // 加密密码
        user.setFPassword(passwordEncoder.encode(user.getFPassword()));
        return userDAO.add(user);
    }

    /**
     * 更新用户
     *
     * @param user TUser实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean updateUser(TUser user) {
        // 如果密码已更改，则重新加密
        TUser existingUser = userDAO.findById(user.getFID());
        if (existingUser != null && !passwordEncoder.matches(user.getFPassword(), existingUser.getFPassword())) {
            user.setFPassword(passwordEncoder.encode(user.getFPassword()));
        } else if (existingUser != null) {
            // 如果密码未更改，保持原密码
            user.setFPassword(existingUser.getFPassword());
        }
        return userDAO.update(user);
    }

    /**
     * 删除用户
     *
     * @param fID 用户ID
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean deleteUser(int fID) {
        return userDAO.delete(fID);
    }

    /**
     * 查询用户
     *
     * @param whereClause SQL WHERE 子句
     * @return 用户列表
     */
    public List<TUser> queryUsers(String whereClause) {
        return userDAO.queryModelList(whereClause);
    }

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限集合
     */
    public Set<String> getUserPermissions(int userId) {
        TUser user = userDAO.findById(userId);
        if (user == null) {
            return Collections.emptySet();
        }
        Set<String> permissions = new HashSet<>();
        String orgIDs = user.getFOrgIDs();
        if (orgIDs == null || orgIDs.isEmpty()) {
            return permissions;
        }
        String[] orgIDArray = orgIDs.split("\\|");
        for (String orgID : orgIDArray) {
            if (orgID == null || orgID.trim().isEmpty() || orgID.equals("0")) {
                continue;
            }
            collectPermissions(orgID, permissions);
        }
        return permissions;
    }

    /**
     * 递归收集权限
     *
     * @param orgID       组织ID
     * @param permissions 权限集合
     */
    private void collectPermissions(String orgID, Set<String> permissions) {
        TOrganization org = organizationDAO.findById(orgID);
        if (org != null) {
            String perms = org.getFPermissions();
            if (perms != null && !perms.trim().isEmpty()) {
                String[] permArray = perms.split("\\|");
                for (String perm : permArray) {
                    if (perm != null && !perm.trim().isEmpty()) {
                        permissions.add(perm.trim());
                    }
                }
            }
            // 收集子组织的权限
            List<TOrganization> subOrgs = organizationDAO.queryModelList("fHigherUpfIDs LIKE '%|" + orgID + "|%'");
            for (TOrganization subOrg : subOrgs) {
                collectPermissions(subOrg.getFID(), permissions);
            }
        }
    }

    /**
     * 加载组织树
     *
     * @return 组织树列表
     */
    public List<OrganizationTreeNode> loadOrganizationTree() {
        List<TOrganization> organizations = organizationDAO.queryModelList(null);
        if (organizations == null) {
            return Collections.emptyList();
        }

        Map<String, OrganizationTreeNode> nodeMap = new HashMap<>();
        List<OrganizationTreeNode> roots = new ArrayList<>();

        // 创建节点映射
        for (TOrganization org : organizations) {
            nodeMap.put(org.getFID(), new OrganizationTreeNode(org));
        }

        // 构建树
        for (TOrganization org : organizations) {
            String parentIDs = org.getFHigherUpfIDs();
            if (parentIDs == null || parentIDs.isEmpty() || parentIDs.equals("0")) {
                // 根节点
                roots.add(nodeMap.get(org.getFID()));
            } else {
                String[] parentIDArray = parentIDs.split("\\|");
                for (String parentID : parentIDArray) {
                    if (parentID == null || parentID.trim().isEmpty() || parentID.equals("0")) {
                        continue;
                    }
                    OrganizationTreeNode parentNode = nodeMap.get(parentID);
                    if (parentNode != null) {
                        parentNode.addChild(nodeMap.get(org.getFID()));
                    }
                }
            }
        }

        return roots;
    }
}
