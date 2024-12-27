// src/main/java/com/example/permissionsystem/dao/TOrganizationDAO.java
package com.example.permissionsystem.dao;

import com.example.permissionsystem.model.TOrganization;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TOrganizationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 增加一条记录
     *
     * @param organization TOrganization实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean add(TOrganization organization) {
        try {
            entityManager.persist(organization);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding TOrganization: " + e.getMessage());
            return false;
        }
    }

    /**
     * 更新一条记录
     *
     * @param organization TOrganization实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean update(TOrganization organization) {
        try {
            entityManager.merge(organization);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating TOrganization: " + e.getMessage());
            return false;
        }
    }

    /**
     * 删除一条记录
     *
     * @param fID 组织ID
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean delete(String fID) {
        try {
            TOrganization organization = entityManager.find(TOrganization.class, fID);
            if (organization != null) {
                entityManager.remove(organization);
                return true;
            } else {
                System.err.println("TOrganization with fID " + fID + " not found.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error deleting TOrganization: " + e.getMessage());
            return false;
        }
    }

    /**
     * 根据查询条件获取实体列表
     *
     * @param whereClause SQL WHERE 子句（不包括 WHERE 关键字）
     * @return TOrganization实体列表
     */
    public List<TOrganization> queryModelList(String whereClause) {
        try {
            String jpql = "SELECT t FROM TOrganization t";
            if (whereClause != null && !whereClause.trim().isEmpty()) {
                jpql += " WHERE " + whereClause;
            }
            TypedQuery<TOrganization> query = entityManager.createQuery(jpql, TOrganization.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error querying TOrganization: " + e.getMessage());
            return null;
        }
    }

    /**
     * 查找组织通过fID
     *
     * @param fID 组织ID
     * @return TOrganization实体或null
     */
    public TOrganization findById(String fID) {
        try {
            return entityManager.find(TOrganization.class, fID);
        } catch (Exception e) {
            System.err.println("Error finding TOrganization by fID: " + e.getMessage());
            return null;
        }
    }
}
