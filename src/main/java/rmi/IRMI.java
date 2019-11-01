package rmi;

import dto.ChatRoomDTO;
import model.Message;
import model.User;
import utils.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRMI extends Remote {

    User verifyUser(String user, String pass) throws RemoteException;

    String checkUserStatus(Integer roomId, int userId) throws RemoteException;

    List<Pair<User, Message>> getOldMessages(int id) throws RemoteException;

    List<Pair<ChatRoomDTO, Integer>> getChatRoomStatusByUser(User user) throws RemoteException;

    ChatRoomDTO getChatRoomDTO(int roomId) throws RemoteException;

    List<ChatRoomDTO> getAllChatRoom() throws RemoteException;

    void saveMessage(Message ms) throws RemoteException;
}
