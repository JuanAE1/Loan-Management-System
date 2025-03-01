package org.juan;

import io.javalin.Javalin;
import org.juan.controller.AuthController;
import org.juan.dao.UserDao;
import org.juan.service.UserService;

public class Main {
    public static void main(String[] args) {

        //Create all DAOs, Services and Controllers (Dependency Injection)
        //USER
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        AuthController authController = new AuthController(userService);
        //LOANS

        //This starts the javalin app
        var app = Javalin.create(/*config*/)
                .start(7070);

        //Endpoints
        //app.get("/roles", userRoleController::getAllRoles);
        app.post("auth/register", authController::register);
        app.post("auth/login", authController::login);
        app.post("auth/logout", authController::logout);
        app.get("auth/check", authController::checkLogin);
    }
}