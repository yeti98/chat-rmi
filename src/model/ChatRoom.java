package model;

import control.MessageController;

import javax.swing.*;
import java.util.ArrayList;

public class ChatRoom {
    private static int fileID;
    private static JTextArea txt;
    private int clientID;
    private ArrayList<Client> clients;
    private String name;
    private int Id;
    private MessageController messageController;

    public MessageController getMessageController() {
        return messageController;
    }

    public void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    public ChatRoom(int Id, ArrayList<Client> clients, String name) {
        this.Id = Id;
        this.clients = clients;
        this.clientID = 0;
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
