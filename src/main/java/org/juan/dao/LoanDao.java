package org.juan.dao;

import org.juan.dto.CreateLoansDto;
import org.juan.dto.GetLoansDto;
import org.juan.model.Loan;
import org.juan.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class LoanDao {

    Connection connection = ConnectionUtil.getConnection();

    public boolean createLoan(Loan newLoan){
        String sql = "INSERT INTO loan (amount, term, created_time, updated_time, type_id, status_id, user_id) " +
                "VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, newLoan.getAmount());
            stmt.setInt(2, newLoan.getTerm());
            stmt.setTimestamp(3, Timestamp.valueOf(newLoan.getCreatedTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(newLoan.getUpdatedTime()));
            stmt.setInt(5, newLoan.getTypeId());
            stmt.setInt(6, newLoan.getStatusId());
            stmt.setInt(7, newLoan.getUserId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<GetLoansDto> getAllLoans() {
        ArrayList<GetLoansDto> loans = new ArrayList<>();
        String sql = "SELECT loan.id, amount, term, type, status, CONCAT(name, ' ', last_name) AS fullName " +
                "FROM loan " +
                "INNER JOIN loan_type ON loan.type_id = loan_type.id " +
                "INNER JOIN status ON loan.status_id = status.id " +
                "INNER JOIN user_account ON loan.user_id = user_account.id;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loans.add(new GetLoansDto(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getInt("term"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("fullName")
                ));
            }
            return loans;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<GetLoansDto> getMyLoans(int userId) {
        ArrayList<GetLoansDto> loans = new ArrayList<>();
        String sql = "SELECT loan.id, amount, term, type, status, CONCAT(name, ' ', last_name) AS fullName " +
                "FROM loan " +
                "INNER JOIN loan_type ON loan.type_id = loan_type.id " +
                "INNER JOIN status ON loan.status_id = status.id " +
                "INNER JOIN user_account ON loan.user_id = user_account.id " +
                "WHERE user_id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loans.add(new GetLoansDto(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getInt("term"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("fullName")
                ));
            }
            return loans;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public GetLoansDto getLoanById(int id){
        String sql = "SELECT loan.id, amount, term, type, status, CONCAT(name, ' ', last_name) AS fullName " +
                "FROM loan " +
                "INNER JOIN loan_type ON loan.type_id = loan_type.id " +
                "INNER JOIN status ON loan.status_id = status.id " +
                "INNER JOIN user_account ON loan.user_id = user_account.id " +
                "WHERE loan.id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new GetLoansDto(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getInt("term"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("fullName")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean updateLoan(CreateLoansDto updatedLoan, int loanId, String timestamp){
        String sql = "UPDATE loan SET amount = ?, term = ?, updated_time = ?, type_id = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, updatedLoan.getAmount());
            stmt.setInt(2, updatedLoan.getTerm());
            stmt.setTimestamp(3, Timestamp.valueOf(timestamp));
            stmt.setInt(4, updatedLoan.getTypeId());
            stmt.setInt(5, loanId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public void approveLoan(int loanId){
        String sql = "UPDATE loan SET status_id = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 2); // the status id of "approve" is 2
            stmt.setInt(2, loanId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean rejectLoan(int loanId){
        String sql = "UPDATE loan SET status_id = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 3); // the status id of "reject" is 3
            stmt.setInt(2, loanId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Integer getLoanOwnerId(int loanId) {
        String sql = "SELECT user_id FROM loan WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
