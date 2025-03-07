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
        int status = authService.createUser(req);
        switch (status){
            case 201:
                ctx.status(201).json("{\"message\":\"User registered successfully\"}");
                break;
            case 400:
                ctx.status(409).json("{\"error\":\"Some fields are missing\"}");
                break;
            case 409:
                ctx.status(409).json("{\"error\":\"An account with this email already exists\"}");
                break;
        }
    }

    public void login (Context ctx){
        AuthDto req = ctx.bodyAsClass(AuthDto.class);
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
        return session != null && session.getAttribute("user") != null;
    }

    public static Integer getLoggedUserId(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("user") != null){
            UserDto loggedUser = (UserDto) session.getAttribute("user");
            return loggedUser.getId();
        } else {
            return null;
        }
    }
    public static Integer getLoggedUserRole(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("user") != null){
            UserDto loggedUser = (UserDto) session.getAttribute("user");
            return loggedUser.getRoleId();
        } else {
            return null;
        }
    }

    public static boolean userIdEqualsSessionId(Context ctx) {
        Integer loggedUserId = AuthController.getLoggedUserId(ctx);
        int requestedUserId = Integer.parseInt(ctx.pathParam("id"));
        if (loggedUserId != null){
            return loggedUserId.equals(requestedUserId);
        }
        return false;
    }
}