package org.juan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String url = "jdbc:postgresql://localhost:5432/lms";
    private static final String username = "postgres";
    private static final String password = "password";

    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}