package dao;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends DBConnector{
    private final Connection connection = DBConnector.getInstance().getConnection();

    public UserDAO() throws SQLException {
        super();
    }

    public User login(String username, String pass) throws SQLException {
        if (username != null && pass != null) {
            String sql = "SELECT * from chatuser Where username='" + username + "' and password='" + pass + "'";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                return user;
            }
        }
        return null;
    }

    public User getUserByID(int id) {
        String sql = "SELECT * from chatuser Where id=" + id;
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
