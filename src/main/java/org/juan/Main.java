package org.juan;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import org.juan.controller.AuthController;
import org.juan.controller.UserController;
import org.juan.dao.UserDao;
import org.juan.service.AuthService;
import org.juan.service.UserService;

public class Main {
    public static void main(String[] args) {

        //Create all DAOs, Services and Controllers (Dependency Injection)
        UserDao userDao = new UserDao();

        AuthService authService = new AuthService(userDao);
        UserService userService = new UserService(userDao);

        AuthController authController = new AuthController(authService, userService);
        UserController userController = new UserController(userService);

        //This starts the javalin app
        var app = Javalin.create(/*config*/)
                .start(7070);

        app.beforeMatched("users/*", ctx -> {
            boolean isLogged = AuthController.checkSession(ctx);
            if(!isLogged){
                throw new UnauthorizedResponse();
            }
        });

        //Auth endpoints
        app.post("auth/register", authController::register);
        app.post("auth/login", authController::login);
        app.post("auth/logout", authController::logout);
        //app.get("auth/check", authController::checkSession);

        //User endpoints
        app.get("users/{id}", userController::getUserById);
        app.put("users/{id}", userController::updateUser);
        app.delete("users/{id}", userController::deleteUser);

        //Loan endpoints
        //app.post("loans", loanController::createLoan);
        //app.get("loans", loanController::getAllLoans);
        //app.get("loans/{1}", loanController::getLoanById);
        //app.put("loans/{1}", loanController::updateLoan);
        //app.patch("loans/{1}/approve", loanController::approveLoan);
        //app.patch("loans/{1}/reject", loanController::rejectLoan);
    }
}