package org.juan.dao;

import org.juan.dto.AuthDto;
import org.juan.dto.UserDto;
import org.juan.model.User;
import org.juan.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<UserDto> getAllUsers(){
        ArrayList<UserDto> users = new ArrayList<>();
        String sql = "SELECT id, email, name, last_name, role_id FROM user_account";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                 users.add(new UserDto(
                         rs.getInt("id"),
                         rs.getString("email"),
                         rs.getString("name"),
                         rs.getString("last_name"),
                         rs.getInt("role_id")
                 ));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDto getUserById(int id){
        String sql = "SELECT id, email, name, last_name, role_id FROM user_account WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new UserDto(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDto getUserByEmail(String email){
        String sql = "SELECT id, email, name, last_name, role_id FROM user_account WHERE email = ?";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new UserDto(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateUser(UserDto updatedUser, int id, Timestamp now){
        String sql = "UPDATE user_account " +
                "SET email = ?, name = ?, last_name = ?, role_id = ?, updated_time = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, updatedUser.getEmail());
            stmt.setString(2, updatedUser.getName());
            stmt.setString(3, updatedUser.getLastName());
            stmt.setInt(4, updatedUser.getRoleId());
            stmt.setTimestamp(5, now);
            stmt.setInt(6, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteUser(int id){
        String sql = "DELETE FROM user_account WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public AuthDto getUserEmailAndPasswordByEmail(String email){
        String sql = "SELECT email, hash_pass FROM user_account WHERE email = ?";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new AuthDto(
                        rs.getString("email"),
                        rs.getString("hash_pass")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
