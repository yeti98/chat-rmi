package control;

import config.AppProperties;
import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MessageController {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public MessageController() throws SQLException {
    }

    public ObjectInputStream getIn() {
        return in;
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
        client = new Socket(IP, AppProperties.PORT);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        out.writeObject(i);
        Message message = new Message();
        message.setStatus("New");
        message.setUser(user);
        message.setRoomId(i);
        message.setCreateAt(new Timestamp(new java.util.Date().getTime()));
        out.writeObject(message);
        out.flush();
    }

}
