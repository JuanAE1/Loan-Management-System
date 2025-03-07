package org.juan.controller;

import io.javalin.http.Context;
import org.juan.dto.UserDto;
import org.juan.service.UserService;

import java.util.ArrayList;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void getAllUsers(Context ctx){
        Integer userRoleId = AuthController.getLoggedUserRole(ctx);
        //check if user is manager
        if (userRoleId != null && userRoleId == 1){
            ArrayList<UserDto> users = userService.getAllUsers();
            if (users != null){
                ctx.status(200).json(users);
            } else {
                ctx.status(500).json("request failed");
            }
        } else {
            ctx.status(403).json("Only a manager can view all users");
        }
    }

    public void getUserById(Context ctx){
        //Check if logged user is requesting their own info
        if (AuthController.userIdEqualsSessionId(ctx)){
            int requestedUserId = Integer.parseInt(ctx.pathParam("id"));
            UserDto user = userService.getUserById(requestedUserId);
            if (user != null){
                ctx.status(200).json(user);
            } else {
                ctx.status(404).json("{\"error\":\"User not found\"}");
            }
        } else {
            ctx.status(403).json("{\"error\":\"You can't see other users info\"}");
        }
    }

    public void updateUser(Context ctx){
        //Check if logged user is requesting its own info
        if (AuthController.userIdEqualsSessionId(ctx)){
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserDto updatedUser = ctx.bodyAsClass(UserDto.class);
            boolean success = userService.updateUser(updatedUser, id);
            if (success){
                ctx.status(200).json("{\"message\":\"User updated successfully\"}");
            } else {
                ctx.status(400).json("{\"error\":\"Missing fields or user doesn't exists\"}");
            }
        } else {
            ctx.status(403).json("{\"error\":\"You can't update other users info\"}");
        }
    }

    public void deleteUser(Context ctx){
        //Check if logged user is requesting its own info
        if (AuthController.userIdEqualsSessionId(ctx)){
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserDto user = userService.getUserById(id);
            //Check that user exists in the database
            if (user != null){
                boolean success = userService.deleteUser(id);
                if (success){
                    ctx.status(200).json("{\"message\":\"User deleted successfully\"}");
                } else {
                    ctx.status(500).json("{\"error\":\"something went wrong with the database\"}");
                }
            } else {
                ctx.status(404).json("{\"error\":\"User not found\"}");
            }
        } else {
            ctx.status(403).json("{\"error\":\"You can't delete other users info\"}");
        }
    }
}