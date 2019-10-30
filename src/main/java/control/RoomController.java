package control;

import dao.ChatRoomDAO;
import model.ChatRoom;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private static List<ChatRoom> rooms = new ArrayList<>();
    private static ChatRoomDAO chatRoomDAO = new ChatRoomDAO();

    public ChatRoom createChatRoom(String name){
        ChatRoom chatRoom = new ChatRoom(rooms.size(), new ArrayList<>(), name);
        return chatRoom;
    }

    public int addClient(Integer roomIdx, Client client) {
        return rooms.get(roomIdx).addClient(client);
    }

    public ChatRoom getRoom(Integer roomIdx) {
        for(ChatRoom chatRoom : rooms){
            if(chatRoom.getId() == roomIdx){
                return chatRoom;
            }
        }
        return null;
    }

    public List<ChatRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<ChatRoom> rooms) {
        this.rooms = rooms;
    }

    public ChatRoom getRoomById(int id) {
        for(ChatRoom chatRoom : rooms){
            if(chatRoom.getId() == id) return chatRoom;
        }
        return null;
    }

    public void loadChatRooms() {
        rooms.addAll(chatRoomDAO.getAllChatRooms());
    }

}
