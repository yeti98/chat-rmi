package rmi;


import server.Server;
import server.ServerSingleton;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteMethod extends UnicastRemoteObject implements IRMI {

    public RemoteMethod() throws RemoteException {
    }

    @Override
    public boolean checkName(String userName) throws RemoteException {
//        System.out.println(userName);
//        for (Client client : Method.getClients()) {
//            String name = client.getUserName();
//            if (name.equalsIgnoreCase(userName)) {
//                return false;
//            }
//        }
        return true;
    }

}
