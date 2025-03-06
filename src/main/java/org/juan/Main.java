package org.juan;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import org.juan.controller.AuthController;
import org.juan.controller.LoanController;
import org.juan.controller.UserController;
import org.juan.dao.LoanDao;
import org.juan.dao.UserDao;
import org.juan.service.AuthService;
import org.juan.service.LoanService;
import org.juan.service.UserService;

public class Main {
    public static void main(String[] args) {

        //Create all DAOs, Services and Controllers (Dependency Injection)
        UserDao userDao = new UserDao();
        LoanDao loanDao = new LoanDao();

        AuthService authService = new AuthService(userDao);
        UserService userService = new UserService(userDao);
        LoanService loanService = new LoanService(loanDao);

        AuthController authController = new AuthController(authService, userService);
        UserController userController = new UserController(userService);
        LoanController loanController = new LoanController(loanService);

        //This starts the javalin app
        var app = Javalin.create(/*config*/)
                .start(7070);

        //this is to block endpoints if there's no user logged in
        app.beforeMatched("users*", Main::checkLogin);
        app.beforeMatched("loans*", Main::checkLogin);

        //Auth endpoints
        app.post("auth/register", authController::register);
        app.post("auth/login", authController::login);
        app.post("auth/logout", authController::logout);

        //User endpoints
        app.get("users", userController::getAllUsers);
        app.get("users/{id}", userController::getUserById);
        app.put("users/{id}", userController::updateUser);
        app.delete("users/{id}", userController::deleteUser);

        //Loan endpoints
        app.get("users/{id}/loans", loanController::getMyLoans);
        app.post("loans", loanController::createLoan);
        app.get("loans", loanController::getAllLoans);
        app.get("loans/{id}", loanController::getLoanById);
        app.put("loans/{id}", loanController::updateLoan);
        app.patch("loans/{id}/approve", loanController::approveLoan);
        app.patch("loans/{id}/reject", loanController::rejectLoan);
    }

    private static void checkLogin(Context ctx) {
        boolean isLogged = AuthController.checkSession(ctx);
        if(!isLogged){
            throw new UnauthorizedResponse();
        }
    }
}