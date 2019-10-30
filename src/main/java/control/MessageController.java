package control;

import model.Friend;
import model.Message;
import model.User;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MessageController {


    private static final String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
    private HashMap<Integer, Friend> friends = new HashMap<>();
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int myID;
    private String time;
    private JFrame fram;

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

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JFrame getFram() {
        return fram;
    }

    public void setFram(JFrame fram) {
        this.fram = fram;
    }

    public void sendMessage(Message message) throws Exception {
        message.setStatus("Message");
        message.setID(this.getMyID());
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String t = df.format(new Date());
        message.setCreateAt(t);
        this.out.writeObject(message);
        this.out.flush();
    }

    public void connect(int i, User user, String IP) throws Exception {
        client = new Socket(IP, 5000);
        this.out = new ObjectOutputStream(client.getOutputStream());
        this.in = new ObjectInputStream(client.getInputStream());
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String t = df.format(new Date());
//        Message ms = new Message();
//        ms.setStatus("New");
//        ms.setImage(null);
//        ms.setName(userName + "!" + t);
        out.writeObject(new Integer(i));
        Message message = new Message();
        message.setStatus("New");
        message.setImage(null);
        message.setUser(user);
        message.setCreateAt(t);
        out.writeObject(message);
        out.flush();
        time = t;
    }
}
