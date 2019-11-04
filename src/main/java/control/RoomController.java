package control;

import dao.ChatRoomDAO;
import dto.ChatRoomDTO;
import model.ChatRoom;
import model.User;
import utils.EntityToDTO;

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

    public ChatRoomDTO createNewChatRoom(String name, User user) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setId(rooms.size()+1);
        chatRoom.getMembers().add(user);
        chatRoom.setMessages(new ArrayList<>());
        chatRoom.setClients(new ArrayList<>());
        this.saveNewChatRoom(chatRoom, user);
        rooms.add(chatRoom);
        return EntityToDTO.chatRoom2ChatRoomDTO(chatRoom);
    }

    public void saveNewChatRoom(ChatRoom chatRoom, User user) {
        chatRoomDAO.saveChatRoom(chatRoom, user.getId());
    }
}
