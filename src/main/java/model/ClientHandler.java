package model;


import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.Timestamp;

public class ClientHandler extends Thread implements Serializable {
    private final Socket socket;
    private User user;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int ID;
    private ChatRoom chatRoom;
    private Timestamp createAt;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.start();
    }


    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Integer roomId = (Integer) in.readObject();
            chatRoom = Server.getRoomController().getRoom(roomId);
            ID = chatRoom.addClient(this);
            //  loop starting get message from client
            while (true) {
                Message ms = (Message) in.readObject();
                String status = ms.getStatus();
                System.out.println("Serverside: " + status);
                switch (status) {
                    case "New":
                        this.user = ms.getUser();
                        this.createAt = ms.getCreateAt();
                        System.out.println("Server side:\n" + chatRoom);
                        System.out.println(("New Client name : " + user.getUsername() + " has connected ...\n"));
                        //  list all friend send to new client login
                        for (ClientHandler client : chatRoom.getClients()) {
                            ms = new Message();
                            ms.setStatus("New");
                            ms.setUser(client.getUser());
                            ms.setCreateAt(client.getCreateAt());
                            out.writeObject(ms);
                            out.flush();
                        }
                        //  send new client to old client
                        ms = new Message();
                        ms.setStatus("New");
                        ms.setUser(user);
                        ms.setCreateAt(this.createAt);
                        sendMessageToOtherClients(ms);
                        break;
                    case "File":
                        break;
                    case "Download":
                        break;
                    case "Quit":
                        ms = new Message();
                        ms.setStatus("Quit");
                        sendMessageToOtherClients(ms);
                        chatRoom.getClients().remove(this);
                        break;
                    default:
                        for (ClientHandler client : chatRoom.getClients()) {
                            client.getOut().writeObject(ms);
                            client.getOut().flush();
                        }
                        break;
                }
            }

        } catch (Exception e) {
            try {
                chatRoom.getClients().remove(this);
                System.out.println("Client Name : " + user.getUsername() + " lost connection...");
                for (ClientHandler s : chatRoom.getClients()) {
                    Message ms = new Message();
                    ms.setStatus("Quit");
                    ms.setUser(user);
                    s.getOut().writeObject(ms);
                    s.getOut().flush();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendMessageToOtherClients(Message ms) throws IOException {
        for (ClientHandler client : chatRoom.getClients()) {
            if (client != this) {
                client.getOut().writeObject(ms);
                client.getOut().flush();
            }
        }
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "user=" + user +
                ", ID=" + ID + "}'";
    }


    private ObjectOutputStream getOut() {
        return out;
    }

    private Timestamp getCreateAt() {
        return createAt;
    }
}
