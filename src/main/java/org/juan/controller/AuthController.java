package org.juan.controller;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.juan.dto.UserDto;
import org.juan.model.User;
import org.juan.service.AuthService;

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
                authService.createUser(req.getEmail(), req.getPassword(), req.getName(), req.getLastName(), req.getRoleId());

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

        User authUser = authService.checkUserCredentials(req.getEmail(), req.getPassword());
        if (authUser != null){
            // Session
            HttpSession session = ctx.req().getSession(true);
            session.setAttribute("user", authUser);
            ctx.status(200).json("{\"Message\": \"Login successful\"}");
        } else {
            ctx.status(401).json("{\"error\": \"Invalid credentials\"}");
        }
    }
    public void logout (Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ctx.status(200).json("{\"message\":\"Logged out\"}");
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