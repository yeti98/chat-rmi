package control;

import model.ChatRoom;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private static List<ChatRoom> rooms = new ArrayList<>();

    public ChatRoom createChatRoom(String name){
        ChatRoom chatRoom = new ChatRoom(rooms.size(), new ArrayList<>(), name);
        rooms.add(chatRoom);
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
}
