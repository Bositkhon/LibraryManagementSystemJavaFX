package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private static Db instance;
    private Connection connection;
    private String url = "jdbc:derby:LibraryManagementSystemDB";
    private String username = "Bositkhon";
    private String password = "1q2w3e4r5t";

    private Db() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Db getInstance() throws SQLException {
        if (instance == null) {
            instance = new Db();
        } else if (instance.getConnection().isClosed()) {
            instance = new Db();
        }

        return instance;
    }
}