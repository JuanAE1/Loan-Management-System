package org.juan;

import io.javalin.Javalin;
import org.juan.controller.UserRoleController;

public class Main {
    public static void main(String[] args) {
        UserRoleController userRoleController = new UserRoleController();
        var app = Javalin.create(/*config*/)
                .start(7070);

        app.get("/roles", userRoleController::getAllRoles);
    }
}