package dto;

import model.Message;
import model.User;
import utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomDTO implements Serializable {
    private int Id;
    private String name;
    private List<User> members = new ArrayList<>();
    private List<Pair<User, Message>> messages = new ArrayList<>();

    public ChatRoomDTO(int id, String name, List<User> members, List<Pair<User, Message>> messages) {
        Id = id;
        this.name = name;
        this.members = members;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public List<Pair<User, Message>> getMessages() {
        return messages;
    }

    public int getId() {
        return Id;
    }

    public List<User> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return Id + " - " + name;
    }
}
