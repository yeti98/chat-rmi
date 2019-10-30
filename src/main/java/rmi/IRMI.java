package rmi;

import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRMI extends Remote {

    User verifyUser(String user, String pass) throws RemoteException;

    String checkUserStatus(Integer roomId, int userId) throws RemoteException;
}
