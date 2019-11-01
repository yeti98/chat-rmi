package dao;

import model.ChatRoom;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomDAO extends DBConnector {
    private final Connection connection = DBConnector.getInstance().getConnection();
    private final UserDAO userDAO = new UserDAO();
    private final MessageDAO messageDAO = new MessageDAO();

    public ChatRoomDAO() throws SQLException {
        super();
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
            for(ChatRoom chatRoom : chatRooms){
                chatRoom.setMessages(messageDAO.getMessagesByRoomId(chatRoom.getId()));
            }
            return chatRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
