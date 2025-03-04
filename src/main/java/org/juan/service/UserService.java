package org.juan.service;

import org.juan.dao.UserDao;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
