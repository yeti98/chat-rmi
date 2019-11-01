package model;

import control.MessageController;
import dto.ChatRoomDTO;
import utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Serializable {
    private ArrayList<ClientHandler> clients;
    private String name;
    private int Id;
    private MessageController messageController;
    private List<User> members = new ArrayList<>();
    private List<Pair<User, Message>> messages = new ArrayList<>();

    public ChatRoom() {
    }

    public ChatRoom(ChatRoomDTO chatRoomDTO, MessageController messageController) {
        this.Id = chatRoomDTO.getId();
        this.name = chatRoomDTO.getName();
        this.messages = chatRoomDTO.getMessages();
        this.members = chatRoomDTO.getMembers();
        this.clients = new ArrayList<>();
        this.messageController = messageController;
    }


    public List<User> getMembers() {
        return members;
    }

    public MessageController getMessageController() {
        return messageController;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public ArrayList<ClientHandler> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ClientHandler> clients) {
        this.clients = clients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int addClient(ClientHandler client) {
        this.clients.add(client);
        return this.clients.size();
    }

    public List<Pair<User, Message>> getMessages() {
        return messages;
    }

    public void setMessages(List<Pair<User, Message>> messages) {
        this.messages = messages;
    }


    @Override
    public String toString() {
        return "ChatRoom{" +
                "clients=" + clients +
                ", name='" + name + '\'' +
                ", Id=" + Id +
                ", members=" + members +
                '}';
    }
}
