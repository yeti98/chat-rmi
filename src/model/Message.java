package model;

import javax.swing.*;
import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {

    private String status;
    private int ID;
    private String username;
    private String createAt;
    private String message;
    private ImageIcon image;
    private byte[] data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status='" + status + '\'' +
                ", ID=" + ID +
                ", username='" + username + '\'' +
                ", createAt='" + createAt + '\'' +
                ", message='" + message + '\'' +
                ", image=" + image +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
