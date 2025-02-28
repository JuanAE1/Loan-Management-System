package org.juan;

import io.javalin.Javalin;
import org.juan.controller.UserController;
import org.juan.dao.UserDao;
import org.juan.service.UserService;

public class Main {
    public static void main(String[] args) {

        //Create all DAOs, Services and Controllers (Dependency Injection)
        //USER
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);
        //LOANS

        //This starts the javalin app
        var app = Javalin.create(/*config*/)
                .start(7070);

        //Endpoints
        //app.get("/roles", userRoleController::getAllRoles);
        app.post("/register", userController::register);
        app.post("/login", userController::login);
    }
}