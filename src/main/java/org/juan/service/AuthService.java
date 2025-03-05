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

    public boolean createUser(String email, String pass, String name, String lastName, Integer roleId) {
        //Check if user already exists
        if (userDao.getUserByEmail(email) != null) {
            return false;
        }
        //encrypt password with Bcrypt
        String hashPassword = BCrypt.hashpw(pass,BCrypt.gensalt());
        //timestamp YYYY-MM-DD HH:MI:SS
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        //creates the user object and sends it to the dao layer
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashPassword);
        newUser.setName(name);
        newUser.setLastName(lastName);
        newUser.setCreatedTime(timeStamp);
        newUser.setUpdatedTime(timeStamp);
        newUser.setRoleId(roleId);

        userDao.createUser(newUser);
        return true;
    }

    public Boolean checkUserCredentials(String email, String pass){
        AuthDto credentials = userDao.getUserEmailAndPasswordByEmail(email);
        //Check if user exists
        if ( credentials == null){
            return false;
        }
        //check is password is correct
        if(BCrypt.checkpw(pass, credentials.getPassword())){
            return true;
        };
        //return null if password is incorrect
        return false;
    }
}
