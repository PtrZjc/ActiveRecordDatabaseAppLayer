package pl.zajacp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static void main(String[] args) {
        getConnection();
        System.out.println("hw");
    }


    private static final String dbName = "testowa";
    private static final String URL = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static Connection openConnection = null;

    public static Connection getConnection() {
        try {
            if (openConnection == null || openConnection.isClosed()) {
//                openConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LearnToCodePortalDB?" +
//                        "useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=" +
//                        "false&serverTimezone=UTC", "root", "password");
                openConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return openConnection;
    }
}
