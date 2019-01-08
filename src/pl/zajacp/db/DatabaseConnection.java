package pl.zajacp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_NAME = "LearnToCodePortalDB";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static Connection openConnection = null;

    public static Connection getConnection() throws SQLException {

            if (openConnection == null || openConnection.isClosed()) {
                openConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        return openConnection;
    }
}
