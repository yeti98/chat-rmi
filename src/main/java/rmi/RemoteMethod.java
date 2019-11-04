package rmi;


import com.mysql.cj.admin.ServerController;
import control.RoomController;
import dto.ChatRoomDTO;
import model.ChatRoom;
import model.ClientHandler;
import model.Message;
import model.User;
import server.Server;
import utils.EntityToDTO;
import utils.Pair;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoteMethod extends UnicastRemoteObject implements IRMI {

    public RemoteMethod() throws RemoteException {
    }

    private RoomController getRoomController() {
        return Server.getRoomController();
    }

    public User verifyUser(String userName, String pass) throws RemoteException {
        try {
            return Server.getUserDAO().login(userName, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String checkUserStatus(Integer roomId, int userId) throws RemoteException {
        ChatRoom chatRoom = getRoomController().getRoomById(roomId);
        List<ClientHandler> clients = chatRoom.getClients();
        for (ClientHandler client : clients) {
            if (client.getUser().getId() == userId) {
                return "Online";
            }
        }
        return "Offline";
    }

    @Override
    public List<Pair<User, Message>> getOldMessages(int roomId) {
        ChatRoom chatRoom = getRoomController().getRoomById(roomId);
        return chatRoom.getMessages();
    }

    @Override
    public List<Pair<ChatRoomDTO, Integer>> getChatRoomStatusByUser(User user) throws RemoteException {
        List<Pair<ChatRoomDTO, Integer>> status = new ArrayList<>();
        List<ChatRoom> chatRooms = getRoomController().getRooms();
        for (ChatRoom chatRoom : chatRooms) {
            System.out.println(chatRoom.getMembers());
            System.out.println(user);
            if (chatRoom.getMembers().contains(user)) {
                ChatRoomDTO chatRoomDTO = EntityToDTO.chatRoom2ChatRoomDTO(chatRoom);
                int numberOfOnline = 0;
                for (User user1 : chatRoom.getMembers()) {
                    if (!user.equals(user1)) {
                        if (checkUserStatus(chatRoom.getId(), user1.getId()).equals("Online")) {
                            numberOfOnline += 1;
                        }
                    }
                }
                status.add(new Pair<>(chatRoomDTO, numberOfOnline));
            }
        }
        return status;
    }

    @Override
    public ChatRoomDTO getChatRoomDTO(int roomId) throws RemoteException {
        ChatRoom chatRoom = getRoomController().getRoomById(roomId);
        return EntityToDTO.chatRoom2ChatRoomDTO(chatRoom);
    }

    @Override
    public List<ChatRoomDTO> getAllChatRoom() throws RemoteException {
        List<ChatRoom> rooms = getRoomController().getRooms();
        return rooms.stream().map(EntityToDTO::chatRoom2ChatRoomDTO).collect(Collectors.toList());
    }

    @Override
    public void saveMessage(Message ms) throws RemoteException {
        Server.getMessageDAO().saveMessage(ms);
    }

    @Override
    public ChatRoomDTO createChatRoom(String name, User user) throws RemoteException {
        return Server.getRoomController().createNewChatRoom(name, user);
    }

    @Override
    public List<User> searchUserByUsername(String key) throws RemoteException {
        return Server.getUserDAO().searchUserByUsername(key);
    }

    @Override
    public void addUserToChatRoom(User user, ChatRoomDTO chatRoomDTO) throws RemoteException {
        RoomController roomController = getRoomController();
        ChatRoom chatRoom = roomController.getRoomById(chatRoomDTO.getId());
        chatRoom.getMembers().add(user);
        roomController.saveNewChatRoom(chatRoom, user);
    }


}
