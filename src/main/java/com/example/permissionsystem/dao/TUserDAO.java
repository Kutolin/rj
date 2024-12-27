// src/main/java/com/example/permissionsystem/dao/TUserDAO.java
package com.example.permissionsystem.dao;

import com.example.permissionsystem.model.TUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 增加一条记录
     *
     * @param user TUser实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean add(TUser user) {
        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding TUser: " + e.getMessage());
            return false;
        }
    }

    /**
     * 更新一条记录
     *
     * @param user TUser实体
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean update(TUser user) {
        try {
            entityManager.merge(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating TUser: " + e.getMessage());
            return false;
        }
    }

    /**
     * 删除一条记录
     *
     * @param fID 用户ID
     * @return 成功返回true，失败返回false
     */
    @Transactional
    public boolean delete(int fID) {
        try {
            TUser user = entityManager.find(TUser.class, fID);
            if (user != null) {
                entityManager.remove(user);
                return true;
            } else {
                System.err.println("TUser with fID " + fID + " not found.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error deleting TUser: " + e.getMessage());
            return false;
        }
    }

    /**
     * 根据查询条件获取实体列表
     *
     * @param whereClause SQL WHERE 子句（不包括 WHERE 关键字）
     * @return TUser实体列表
     */
    public List<TUser> queryModelList(String whereClause) {
        try {
            String jpql = "SELECT t FROM TUser t";
            if (whereClause != null && !whereClause.trim().isEmpty()) {
                jpql += " WHERE " + whereClause;
            }
            TypedQuery<TUser> query = entityManager.createQuery(jpql, TUser.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error querying TUser: " + e.getMessage());
            return null;
        }
    }

    /**
     * 查找用户通过fID
     *
     * @param fID 用户ID
     * @return TUser实体或null
     */
    public TUser findById(int fID) {
        try {
            return entityManager.find(TUser.class, fID);
        } catch (Exception e) {
            System.err.println("Error finding TUser by fID: " + e.getMessage());
            return null;
        }
    }
}
