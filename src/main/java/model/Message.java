package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {

    private int id;
    private String status;
    private User user;
    private Timestamp createAt;
    private String content;
    private int roomId;

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
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", createAt=" + createAt +
                ", content='" + content + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}
