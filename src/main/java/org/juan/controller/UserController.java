package org.juan.controller;

import io.javalin.http.Context;
import org.juan.service.AuthService;
import org.juan.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void getUserById(Context ctx){

    }
}
