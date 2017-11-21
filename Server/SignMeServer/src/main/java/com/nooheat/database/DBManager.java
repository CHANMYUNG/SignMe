package com.nooheat.database;

import java.sql.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class DBManager {
    private static Connection connection;
    private static final String URL = System.getenv("SIGNME_DB_URL");
    private static final String USER = System.getenv("SIGNME_DB_USER");
    private static final String PASSWORD = System.getenv("SIGNME_DB_PASSWORD");

    private DBManager() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static ResultSet execute(String query) {
        ResultSet rs = null;
        System.out.println(query);
        try {

            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public synchronized static ResultSet execute(String query, Object... param) {
        ResultSet rs = null;
        System.out.println(getPrepared(query, param).toString());
        try {
            rs = getPrepared(query, param).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public synchronized static int update(String query, Object... param) throws SQLException {
        int affectedRows = -1;


        System.out.println(getPrepared(query, param).toString());
        affectedRows = getPrepared(query, param).executeUpdate();


        return affectedRows;
    }

    private synchronized static PreparedStatement getPrepared(String query, Object... param) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            int paramCount = 1;
            for (Object o : param) {
                statement.setObject(paramCount++, o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public synchronized static void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
