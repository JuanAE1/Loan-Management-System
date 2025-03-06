package org.juan.controller;

import io.javalin.http.Context;
import org.juan.dto.CreateLoansDto;
import org.juan.dto.GetLoansDto;
import org.juan.service.LoanService;

import java.util.ArrayList;

public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public void createLoan(Context ctx){
        CreateLoansDto newLoan = ctx.bodyAsClass(CreateLoansDto.class);
        Integer userId = AuthController.getLoggedUserId(ctx);
        if (userId != null){
            if(newLoan.getAmount() == null || newLoan.getTerm() == null || newLoan.getTypeId() == null){
                ctx.status(400).json("Field missing");
                return;
            }
            boolean success = loanService.createLoan(newLoan, userId);
            if (success){
                ctx.status(201).json("Loan created");
            } else {
                ctx.status(500).json("something went bad with db");
            }
        }
    }

    public void getAllLoans(Context ctx){
        //check that logged user is a manager
        Integer userRoleId = AuthController.getLoggedUserRole(ctx);
        if (userRoleId != null && userRoleId == 1){
            ArrayList<GetLoansDto> loans = loanService.getAllLoans();
            ctx.status(200).json(loans);
        } else {
            ctx.status(403).json("Only a manager can view all loans");
        }
    }

    public void getMyLoans(Context ctx){
        Integer userId = AuthController.getLoggedUserId(ctx);
        int reqId = Integer.parseInt(ctx.pathParam("id"));
        if (userId != null && userId.equals(reqId)){
            ArrayList<GetLoansDto> loans = loanService.getMyLoans(reqId);
            ctx.status(200).json(loans);
        } else {
            ctx.status(403).json("You can't view other people loans");
        }
    }

    public void getLoanById(Context ctx){
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        //variables i need for the security checks
        Integer userRoleId = AuthController.getLoggedUserRole(ctx);
        Integer userId = AuthController.getLoggedUserId(ctx);
        Integer loanOwnerId = loanService.getLoanOwnerId(loanId);
        //gets the loan
        GetLoansDto loan = loanService.getLoanById(loanId);
        //check if loan exists
        if (loan != null){
            //check if user is manager (the manager role id is = 1, maybe later it should work differently)
            if (userRoleId != null && userRoleId == 1){
                ctx.status(200).json(loan);
            } else if (userId != null && userId.equals(loanOwnerId)) { //else check if user is the owner of the loan
                ctx.status(200).json(loan);
            } else {
                ctx.status(403).json("you have no access to this loan");
            }
        } else {
            ctx.status(404).json("Loan not found");
        }
    }

    public void updateLoan(Context ctx){
        CreateLoansDto updatedLoan = ctx.bodyAsClass(CreateLoansDto.class);
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        //variables for the checks
        Integer userId = AuthController.getLoggedUserId(ctx);
        Integer loanOwnerId = loanService.getLoanOwnerId(loanId);
        //check fields
        if(updatedLoan.getAmount() == null || updatedLoan.getTerm() == null || updatedLoan.getTypeId() == null){
            ctx.status(400).json("Field missing");
        } else {
            //check if user is the owner of the loan
            if (userId != null && userId.equals(loanOwnerId)) {
                boolean success = loanService.updateLoan(updatedLoan, loanId);
                if (success){
                    ctx.status(200).json("loan updated");
                } else {
                    ctx.status(500).json("Something went wrong from our side");
                }
            } else {
                ctx.status(403).json("you have no access to this loan");
            }
        }
    }

    public void approveLoan(Context ctx){
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        Integer userRoleId = AuthController.getLoggedUserRole(ctx);
        if (userRoleId != null && userRoleId == 1){
            boolean success = loanService.approveLoan(loanId);
            if (success){
                ctx.status(200).json("Loan approved");
            } else {
                ctx.status(500).json("request failed");
            }
        } else {
            ctx.status(403).json("Only a manager can perform this action");
        }
    }

    public void rejectLoan(Context ctx){
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        Integer userRoleId = AuthController.getLoggedUserRole(ctx);
        if (userRoleId != null && userRoleId == 1){
            boolean success = loanService.rejectLoan(loanId);
            if (success){
                ctx.status(200).json("Loan rejected");
            } else {
                ctx.status(500).json("request failed");
            }
        } else {
            ctx.status(403).json("Only a manager can perform this action");
        }
    }
}
