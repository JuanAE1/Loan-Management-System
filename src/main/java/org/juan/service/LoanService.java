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
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        Loan loan = new Loan(
                null,
                newLoan.getAmount(),
                newLoan.getTerm(),
                timeStamp,
                timeStamp,
                newLoan.getTypeId(),
                2,
                userId
        );
        return loanDao.createLoan(loan);
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
        //timestamp YYYY-MM-DD HH:MI:SS
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return loanDao.updateLoan(updatedLoan, loanId, timeStamp);
    }

    public boolean approveLoan(int loanId){
        return loanDao.approveLoan(loanId);
    }

    public boolean rejectLoan(int loanId){
        return loanDao.rejectLoan(loanId);
    }

}
