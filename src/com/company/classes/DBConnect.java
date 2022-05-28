package com.company.classes;

import java.sql.*;

public class DBConnect {
    public  String url = "jdbc:sqlite:db.sqlite";
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    public void conn() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void disconn() {
        if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException ignored) {}
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
    }
}
