package org.juan.service;

import org.juan.dao.UserRoleDao;
import org.juan.model.UserRole;

import java.util.List;

public class UserRoleService {

    UserRoleDao userRoleDao = new UserRoleDao();

    public List<UserRole> getAllRoles(){
        return userRoleDao.getAllRoles();
    }
}