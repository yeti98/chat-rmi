package control;

import dao.ChatRoomDAO;
import model.ChatRoom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private static ChatRoomDAO chatRoomDAO = null;

    static {
        try {
            chatRoomDAO = new ChatRoomDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<ChatRoom> rooms = new ArrayList<>();

    public ChatRoom getRoom(Integer roomIdx) {
        for (ChatRoom chatRoom : rooms) {
            if (chatRoom.getId() == roomIdx) {
                return chatRoom;
            }
        }
        return null;
    }

    public List<ChatRoom> getRooms() {
        return rooms;
    }

    public ChatRoom getRoomById(int id) {
        for (ChatRoom chatRoom : rooms) {
            if (chatRoom.getId() == id) return chatRoom;
        }
        return null;
    }

    public void loadChatRooms() {
        rooms.addAll(chatRoomDAO.getAllChatRooms());
    }

}
