package org.juan.controller;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.juan.dto.AuthDto;
import org.juan.dto.UserDto;
import org.juan.model.User;
import org.juan.service.AuthService;
import org.juan.service.UserService;

public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    public void register (Context ctx){
        User req = ctx.bodyAsClass(User.class);
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
                authService.createUser(req.getEmail(), req.getPassword(), req.getName(), req.getLastName(), req.getRoleId());

        if (success){
            ctx.status(201).json("{\"message\":\"User registered successfully\"}");
        } else {
            ctx.status(409).json("{\"error\":\"An account with this email already exists\"}");
        }
    }
    public void login (Context ctx){
        AuthDto req = ctx.bodyAsClass(AuthDto.class);
        if(req.getEmail() == null || req.getPassword() == null){
            ctx.status(400).json("{\"error\": \"Missing email or password\" }");
        }

        boolean validCredentials = authService.checkUserCredentials(req.getEmail(), req.getPassword());
        if (validCredentials){
            //get user
            UserDto user = userService.getUserByEmail(req.getEmail());
            // Session
            HttpSession session = ctx.req().getSession(true);
            session.setAttribute("user", user);
            ctx.status(200).json("{\"Message\": \"Login successful\"}");
        } else {
            ctx.status(401).json("{\"error\": \"Invalid credentials\"}");
        }
    }
    public void logout (Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
            ctx.status(200).json("{\"message\":\"Logged out\"}");
        } else {
            ctx.status(404).json("{\"warning\":\"No session found\"}");
        }

    }
    public static boolean checkSession(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("user") != null){
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
            return true;
        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
            return false;
        }
    }
}