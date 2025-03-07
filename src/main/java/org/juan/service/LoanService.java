package org.juan.service;

import org.juan.dao.LoanDao;
import org.juan.dto.CreateLoansDto;
import org.juan.dto.GetLoansDto;
import org.juan.model.Loan;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LoanService {
    private final LoanDao loanDao;

    public LoanService(LoanDao loanDao){
        this.loanDao = loanDao;
    }

    public boolean createLoan(CreateLoansDto newLoan, int userId){
        if (newLoan.getAmount() != null && newLoan.getTerm() != null && newLoan.getTypeId() != null){
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Loan loan = new Loan(
                    null,
                    newLoan.getAmount(),
                    newLoan.getTerm(),
                    timeStamp,
                    timeStamp,
                    newLoan.getTypeId(),
                    1,//default status id "Pending"
                    userId
            );
            return loanDao.createLoan(loan);
        } else {
            return false;
        }
    }

    public ArrayList<GetLoansDto> getAllLoans(){
        return loanDao.getAllLoans();
    }

    public ArrayList<GetLoansDto> getMyLoans(int userId){
        return loanDao.getMyLoans(userId);
    }

    public GetLoansDto getLoanById(int id){
        return loanDao.getLoanById(id);
    }

    public Integer getLoanOwnerId (int loanId) {
        return loanDao.getLoanOwnerId(loanId);
    }

    public boolean updateLoan(CreateLoansDto updatedLoan, int loanId){
        //check fields
        if(updatedLoan.getAmount() != null && updatedLoan.getTerm() != null && updatedLoan.getTypeId() != null){
            //timestamp YYYY-MM-DD HH:MI:SS
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            return loanDao.updateLoan(updatedLoan, loanId, timeStamp);
        } else {
            return false;
        }
    }

    public int approveLoan(int loanId){
        //I need to check if loan exists
        GetLoansDto loan = getLoanById(loanId);
        if(loan != null){
            loanDao.approveLoan(loanId);
            return 200;
        } else {
            return 404;
        }
    }

    public int rejectLoan(int loanId){
        //I need to check if loan exists
        GetLoansDto loan = getLoanById(loanId);
        if(loan != null){
            loanDao.rejectLoan(loanId);
            return 200;
        } else {
            return 404;
        }
    }

}
