package dao;


import model.Message;
import model.User;
import utils.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection connection = DatabaseConnection.getInstance().getConnection();
    private UserDAO userDAO = new UserDAO();

    public boolean saveMessage(Message message) {
        String query = " insert into message (userid, roomid, content, createAt)" + " values (?, ?, ?, ?)";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            java.util.Date d = new java.util.Date();
            java.sql.Date sd = new java.sql.Date(d.getTime());
            preparedStmt.setInt(1, message.getUser().getId());
            preparedStmt.setInt(2, message.getRoomId());
            preparedStmt.setString(3, message.getContent());
            preparedStmt.setTimestamp(4, message.getCreateAt());
            return preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public List<Pair<User, Message>> getMessagesByRoomId(int roomId) {
        String sql = "SELECT * from message Where roomid=" + roomId;
        Statement statement = null;
        try {
            List<Pair<User, Message>> messages = new ArrayList<>();
            statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt(1));
                User user = userDAO.getUserByID(rs.getInt(2));
                message.setUser(user);
                message.setRoomId(rs.getInt(3));
                message.setContent(rs.getString(4));
                message.setCreateAt(rs.getTimestamp(5));
                message.setStatus("Message");
                messages.add(new Pair<>(user, message));
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
