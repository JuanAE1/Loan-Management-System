package org.juan.controller;

import io.javalin.http.Context;
import org.juan.dto.UserDto;
import org.juan.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void getUserById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        UserDto user = userService.getUserById(id);
        if (user != null){
            ctx.status(200).json(user);
        } else {
            ctx.status(404).json("{\"error\":\"User not found\"}");
        }
    }
    public void updateUser(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        UserDto updatedUser = ctx.bodyAsClass(UserDto.class);

        if(updatedUser.getEmail() == null || updatedUser.getName() == null ||
                updatedUser.getLastName() == null || updatedUser.getRoleId() == null){
            ctx.status(400).json("{\"error\":\"Missing field\"}");
        } else {
            UserDto user = userService.getUserById(id);
            if (user != null){
                boolean success = userService.updateUser(updatedUser, id);
                if (success){
                    ctx.status(200).json("{\"message\":\"User updated successfully\"}");
                } else {
                    ctx.status(500).json("{\"error\":\"something went wrong with the database\"}");
                }
            } else {
                ctx.status(404).json("{\"error\":\"User not found\"}");
            }
        }
    }
    public void deleteUser(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        UserDto user = userService.getUserById(id);
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
    }
}