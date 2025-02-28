package org.juan.controller;

import io.javalin.http.Context;
import org.juan.dto.UserDto;
import org.juan.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void register (Context ctx){
        UserDto req = ctx.bodyAsClass(UserDto.class);
        //Check if there's any missing fields
        if(req.getEmail() == null || req.getPassword() == null ||
                req.getName() == null || req.getLastName() == null){
            ctx.status(400).json("{\"error\":\"Missing a field\"}");
            return;
        }
        //If role is missing by default it's a regular user (roleId = 2)
        if(req.getRoleId() == null){
            req.setRoleId(2);
        }

        boolean success =
                userService.createUser(req.getEmail(), req.getPassword(), req.getName(), req.getLastName(), req.getRoleId());

        if (success){
            ctx.status(201).json("{\"message\":\"User registered successfully\"}");
        } else {
            ctx.status(409).json("{\"error\":\"An account with this email already exists\"}");
        }
    }
    public void login (Context ctx){
        UserDto req = ctx.bodyAsClass(UserDto.class);
        if(req.getEmail() == null || req.getPassword() == null){
            ctx.status(400).json("{\"error\": \"Missing email or password\" }");
        }

        boolean success = userService.loginUser(req.getEmail(), req.getPassword());
        if (success){
            // Typically you might return a JWT or session token here
            // For now we will pretend they are authenticated without cookies
            ctx.status(200).json("{\"Message\": \"Login successful\"}");
        } else {
            ctx.status(401).json("{\"error\": \"Invalid credentials\"}");
        }
    }
}