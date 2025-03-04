package org.juan;

import io.javalin.Javalin;
import org.juan.controller.AuthController;
import org.juan.controller.UserController;
import org.juan.dao.AuthDao;
import org.juan.dao.UserDao;
import org.juan.service.AuthService;
import org.juan.service.UserService;

public class Main {
    public static void main(String[] args) {

        //Create all DAOs, Services and Controllers (Dependency Injection)
        //AUTH
        AuthDao authDao = new AuthDao();
        AuthService authService = new AuthService(authDao);
        AuthController authController = new AuthController(authService);

        //USER
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);

        // LOANS

        //This starts the javalin app
        var app = Javalin.create(/*config*/)
                .start(7070);

        //Auth endpoints
        app.post("auth/register", authController::register);
        app.post("auth/login", authController::login);
        app.post("auth/logout", authController::logout);
        app.get("auth/check", authController::checkSession);

        //User endpoints
        //app.get("users/{id}", userController::getUserById);
        //app.put("users/{id}", userController::updateUser);
        //app.delete("users/{id}", userController::deleteUser);

        //Loan endpoints
        //app.post("loans", loanController::createLoan);
        //app.get("loans", loanController::getAllLoans);
        //app.get("loans/{1}", loanController::getLoanById);
        //app.put("loans/{1}", loanController::updateLoan);
        //app.patch("loans/{1}/approve", loanController::approveLoan);
        //app.patch("loans/{1}/reject", loanController::rejectLoan);
    }
}