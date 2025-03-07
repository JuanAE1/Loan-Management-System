package org.juan.service;

import org.juan.dao.UserDao;
import org.juan.dto.UserDto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public ArrayList<UserDto> getAllUsers(){
        return userDao.getAllUsers();
    }

    public UserDto getUserById(int id){
        return userDao.getUserById(id);
    }

    public UserDto getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }

    public boolean updateUser(UserDto updatedUser, int id){
        //check for no missing fields
        if(updatedUser.getEmail() == null || updatedUser.getName() == null ||
                updatedUser.getLastName() == null || updatedUser.getRoleId() == null){
            return false;
        } else {
            UserDto user = getUserById(id);
            //check that user exists in the db
            if (user != null){
                //timestamp YYYY-MM-DD HH:MI:SS
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
                return userDao.updateUser(updatedUser, id, Timestamp.valueOf(timeStamp));
            } else {
                return false;
            }
        }
    }

    public boolean deleteUser(int id){
        return userDao.deleteUser(id);
    }
}
