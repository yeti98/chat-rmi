package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static DBConnector instance;
    private final String URL = "jdbc:mysql://localhost:3306/chat";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private Connection connection;

    protected DBConnector() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static DBConnector getInstance() {
        try {
            if (instance == null) {

                instance = new DBConnector();

            } else if (instance.getConnection().isClosed()) {
                instance = new DBConnector();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}