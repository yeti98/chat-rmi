package dao;

import model.ChatRoom;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomDAO {
    private DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
    private Connection connection = databaseConnection.getConnection();
    private UserDAO userDAO = new UserDAO();

    public ChatRoomDAO() {
    }

    public List<ChatRoom> getAllChatRooms() {
        try {
            String sql = "SELECT * from chatroom";
            Statement statement = this.connection.createStatement();
            List<ChatRoom> chatRooms = new ArrayList<>();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int roomId = rs.getInt(1);
                ChatRoom chatRoom = null;
                for (ChatRoom cr : chatRooms) {
                    if (cr.getId() == roomId) {
                        chatRoom = cr;
                        break;
                    }
                }
                User user = userDAO.getUserByID(rs.getInt(3));
                if (chatRoom != null) {
                    chatRoom.getMembers().add(user);
                } else {
                    chatRoom = new ChatRoom();
                    chatRoom.setId(roomId);
                    chatRoom.setName(rs.getString(2));
                    chatRoom.getMembers().add(user);
                    chatRooms.add(chatRoom);
                    chatRoom.setClients(new ArrayList<>());
                }
            }
            return chatRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
