package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable, Comparable<Message> {

    private int id;
    private String status;
    private User user;
    private Timestamp createAt;
    private String content;
    private int roomId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    @Override
    public int compareTo(Message message) {
        return (int) (this.getCreateAt().getTime() - message.getCreateAt().getTime());
    }
}
