package model;

import control.MessageController;
import utils.Pair;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Serializable {
    private static int fileID;
    private static JTextArea txt;
    private int clientID;
    private ArrayList<Client> clients;
    private String name;
    private int Id;
    private MessageController messageController;
    private List<User> members = new ArrayList<>();
    private List<Pair<User, Message>> messages = new ArrayList<>();

    public ChatRoom() {
    }

    public ChatRoom(int Id, ArrayList<Client> clients, String name) {
        this.Id = Id;
        this.clients = clients;
        this.clientID = 0;
        this.name = name;
    }

    public static int getFileID() {
        return fileID;
    }

    public static void setFileID(int aFileID) {
        fileID = aFileID;
    }

    public static JTextArea getTxt() {
        return txt;
    }

    public static void setTxt(JTextArea aTxt) {
        txt = aTxt;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public MessageController getMessageController() {
        return messageController;
    }

    public void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int addClient(Client client) {
        this.clients.add(client);
        return clientID++;
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
                "clientID=" + clientID +
                ", clients=" + clients +
                ", name='" + name + '\'' +
                ", Id=" + Id +
                '}';
    }
}
