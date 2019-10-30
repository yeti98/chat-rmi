package control;

import dao.MessageDAO;
import model.Friend;
import model.Message;
import model.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;

public class MessageController {
    private static final String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
    private HashMap<Integer, Friend> friends = new HashMap<>();
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private MessageDAO messageDAO = new MessageDAO();

    public static String[] getFileSizeUnits() {
        return fileSizeUnits;
    }

    public static void sendPhoto(ImageIcon photo) throws Exception {
//        Message ms = new Message();
//        ms.setStatus("Photo");
//        ms.setID(MessageController.getMyID());
//        ms.setImage(photo);
//        out.writeObject(ms);
//        out.flush();
    }

    public HashMap<Integer, Friend> getFriends() {
        return friends;
    }

    public void setFriends(HashMap<Integer, Friend> friends) {
        this.friends = friends;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }


    public void sendMessage(Message message) {
        try {
            this.out.writeObject(message);
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connect(int i, User user, String IP) throws Exception {
        client = new Socket(IP, 5000);
        this.out = new ObjectOutputStream(client.getOutputStream());
        this.in = new ObjectInputStream(client.getInputStream());
        out.writeObject(new Integer(i));
        Message message = new Message();
        message.setStatus("New");
        message.setUser(user);
        message.setRoomId(i);
        message.setCreateAt(new Timestamp(new java.util.Date().getTime()));
        out.writeObject(message);
        out.flush();
    }

    public boolean saveMessage(Message ms) {
        return messageDAO.saveMessage(ms);
    }

}
