package org.juan.dao;

import org.juan.model.User;
import org.juan.util.ConnectionUtil;

import java.sql.*;

public class UserDao {

    private final Connection connection = ConnectionUtil.getConnection();

    public void createUser(User newUser){
        String sql = "INSERT INTO user_account " +
                "(email, hash_pass, name, last_name, created_time, updated_time, role_id) VALUES (?,?,?,?,?,?,?);";
        //Here we get the connection and then execute the query inside a try-catch block
        try {
            //prepared statement to prevent SQL injection attacks
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newUser.getEmail());
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.getName());
            stmt.setString(4,newUser.getLastName());
            stmt.setTimestamp(5, Timestamp.valueOf(newUser.getCreatedTime()));
            stmt.setTimestamp(6, Timestamp.valueOf(newUser.getUpdatedTime()));
            stmt.setInt(7, newUser.getRoleId());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public User getUserByEmail(String email){
        String sql = "SELECT * FROM user_account WHERE email = ?";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("hash_pass"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("created_time"),
                        rs.getString("updated_time"),
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
