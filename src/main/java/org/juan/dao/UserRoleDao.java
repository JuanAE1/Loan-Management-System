package org.juan.dao;

import org.juan.model.UserRole;
import org.juan.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao {

    public List<UserRole> getAllRoles(){
        List<UserRole> roles = new ArrayList<>();
        String sql = "SELECT * FROM user_role";

        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                UserRole userRole = new UserRole(
                        rs.getInt("id"),
                        rs.getString("role")
                );
                roles.add(userRole);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return roles;
    }
}
