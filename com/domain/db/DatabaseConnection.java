package com.domain.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection openConnection = null;

    public static Connection getConnection() throws SQLException {
        if (openConnection == null || openConnection.isClosed()) {
            Connection newConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/programmingSchoolDB?" +
                    "useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=" +
                    "false&serverTimezone=UTC", "goddy", "qweqwe");
            openConnection = newConnection;
        }
        return openConnection;
    }
}
