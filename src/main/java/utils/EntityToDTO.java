package utils;

import dto.ChatRoomDTO;
import model.ChatRoom;

public class EntityToDTO {
    public static ChatRoomDTO chatRoom2ChatRoomDTO(ChatRoom chatRoom) {
        return new ChatRoomDTO(chatRoom.getId(), chatRoom.getName(), chatRoom.getMembers(), chatRoom.getMessages());
    }
}
