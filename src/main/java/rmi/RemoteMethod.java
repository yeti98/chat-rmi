package rmi;


import dao.UserDAO;
import model.ChatRoom;
import model.Client;
import model.Message;
import model.User;
import server.Server;
import server.ServerSingleton;
import utils.Pair;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class RemoteMethod extends UnicastRemoteObject implements IRMI {

    public RemoteMethod() throws RemoteException {
    }

    public User verifyUser(String userName, String pass) throws RemoteException {
        try {
            return new UserDAO().login(userName, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String checkUserStatus(Integer roomId, int userId) throws RemoteException {
        Server server = ServerSingleton.getServer();
        ChatRoom chatRoom = server.getRoomController().getRoomById(roomId);
        List<Client> clients = chatRoom.getClients();
        for (Client client : clients) {
            if (client.getUser().getId() == userId) {
                return "Online";
            }
        }
        return "Offline";
    }

    @Override
    public List<Pair<User, Message>> getOldMessages(int roomId) {
        Server server = ServerSingleton.getServer();
        ChatRoom chatRoom = server.getRoomController().getRoomById(roomId);
        return chatRoom.getMessages();
    }


}
