package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRMI extends Remote {

    public boolean checkName(String name) throws RemoteException;
}
