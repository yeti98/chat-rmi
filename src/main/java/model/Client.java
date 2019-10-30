package model;


import server.Server;
import server.ServerSingleton;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;

public class Client extends Thread implements Serializable {
    private Socket socket;
    private User user;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int ID;
    private ChatRoom chatRoom;
    private Timestamp createAt;

    public Client(Socket socket) {
        this.socket = socket;
        this.start();
    }


    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Integer roomId = (Integer) in.readObject();
            Server server = ServerSingleton.getServer();
            chatRoom = server.getRoomController().getRoom(roomId);
            ID = chatRoom.addClient(this);
            //  loop starting get message from client
            while (true) {
                Message ms = (Message) in.readObject();
                String status = ms.getStatus();
                System.out.println("Serverside: "+ status);
                if (status.equals("New")) {
                    this.user = ms.getUser();
                    this.createAt = ms.getCreateAt();
                    System.out.println("Server side:\n" + chatRoom);
                    System.out.println(("New Client name : " + user.getUsername() + " has connected ...\n"));
                    //  list all friend send to new client login
                    for (Client client : chatRoom.getClients()) {
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
                } else if (status.equals("File")) {
//                    int fileID = chatRoom.getFileID();
//                    String fileN = ms.getName();
//                    SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyhhmmssaa");
//                    String fileName = fileID + "!" + df.format(new Date()) + "!" + ms.getName().split("!")[0];
//                    chatRoom.getTxt().append(fileName);
//                    FileOutputStream output = new FileOutputStream(new File("data/" + fileName));
//                    output.write(ms.getData());
//                    output.close();
//                    chatRoom.setFileID(fileID + 1);
//                    ms = new Message();
//                    ms.setStatus("File");
//                    ms.setName(fileID + "!" + fileN);
//                    ms.setImage((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(new File("data/" + fileName)));
//                    ms.setID(ID);
//                    for (Client client : chatRoom.getClients()) {
//                        client.getOut().writeObject(ms);
//                        client.getOut().flush();
//                    }
                } else if (status.equals("download")) {
                    sendFile(ms);
                } else if (status.equals("Quit")) {
                    ms = new Message();
                    ms.setStatus("Quit");
                    sendMessageToOtherClients(ms);
                    chatRoom.getClients().remove(this);
                } else {
                    for (Client client : chatRoom.getClients()) {
                        client.getOut().writeObject(ms);
                        client.getOut().flush();
                    }
                }
            }

        } catch (Exception e) {
            try {
                chatRoom.getClients().remove(this);
                System.out.println("Client Name : " + user.getUsername() + " lost connection...");
                for (Client s : chatRoom.getClients()) {
                    Message ms = new Message();
                    ms.setStatus("Error");
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
        for (Client client : chatRoom.getClients()) {
            if (client != this) {
                client.getOut().writeObject(ms);
                client.getOut().flush();
            }
        }
    }

    private void sendFile(Message ms) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fID = ms.getContent();
                File file = new File("data");
                for (File f : file.listFiles()) {
                    if (f.getName().startsWith(fID)) {
                        try {
                            FileInputStream ins = new FileInputStream(f);
                            byte[] data = new byte[ins.available()];
                            ins.read(data);
                            ins.close();
//                            ms.setData(data);
                            ms.setStatus("GetFile");
                            out.writeObject(ms);
                            out.flush();
                            break;
                        } catch (Exception e) {
                            //  send to client error

                        }
                    }
                }
            }
        }).start();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "user=" + user +
                ", ID=" + ID + "}'";
    }


    public ObjectOutputStream getOut() {
        return out;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }
}
