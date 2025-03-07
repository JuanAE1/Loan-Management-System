package org.juan.service;

import org.juan.dao.UserDao;
import org.juan.dto.AuthDto;
import org.juan.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.text.SimpleDateFormat;

public class AuthService {

    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int createUser(User newUser) {
        //Check if there's any missing fields
        if(newUser.getEmail() == null || newUser.getPassword() == null ||
                newUser.getName() == null || newUser.getLastName() == null){
            return 400;
        }
        //Check if user already exists
        if (userDao.getUserByEmail(newUser.getEmail()) != null) {
            System.out.println("already exists");
            return 409;
        }
        //If role is missing by default it's a regular user (roleId = 2)
        if(newUser.getRoleId() == null){
            newUser.setRoleId(2);
        }
        //encrypt password with Bcrypt
        String hashPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashPassword);
        //timestamp YYYY-MM-DD HH:MI:SS
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        newUser.setCreatedTime(timeStamp);
        newUser.setUpdatedTime(timeStamp);
        userDao.createUser(newUser);
        return 201;
    }

    public Boolean checkUserCredentials(String email, String pass){
        if(email != null && pass != null){
            AuthDto credentials = userDao.getUserEmailAndPasswordByEmail(email);
            //Check if user exists
            if ( credentials == null){
                return false;
            }
            //check is password is correct
            return BCrypt.checkpw(pass, credentials.getPassword());
        }
        //return false if any field is missing
        return false;

    }
}
