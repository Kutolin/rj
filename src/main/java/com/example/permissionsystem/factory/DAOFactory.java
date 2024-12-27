// src/main/java/com/example/permissionsystem/factory/DAOFactory.java
package com.example.permissionsystem.factory;

import com.example.permissionsystem.dao.TOrganizationDAO;
import com.example.permissionsystem.dao.TUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DAOFactory {

    private final TOrganizationDAO organizationDAO;
    private final TUserDAO userDAO;

    @Autowired
    public DAOFactory(TOrganizationDAO organizationDAO, TUserDAO userDAO) {
        this.organizationDAO = organizationDAO;
        this.userDAO = userDAO;
    }

    /**
     * 获取TOrganizationDAO实例
     *
     * @return TOrganizationDAO实例
     */
    public TOrganizationDAO getTOrganizationDAO() {
        return organizationDAO;
    }

    /**
     * 获取TUserDAO实例
     *
     * @return TUserDAO实例
     */
    public TUserDAO getTUserDAO() {
        return userDAO;
    }
}
