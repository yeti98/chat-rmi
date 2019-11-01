package server;


import config.AppProperties;
import control.RoomController;
import model.ClientHandler;
import model.Message;
import model.User;
import rmi.RemoteMethod;
import utils.Pair;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Server extends JFrame {
    private static final RoomController roomController = new RoomController();
    private ServerSocket server;
    private Thread run;

    private Server() {
        roomController.loadChatRooms();
    }

    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static RoomController getRoomController() {
        return roomController;
    }


    private void startServer() throws Exception {
        server = new ServerSocket(AppProperties.PORT);
        run = new Thread(() -> {
            try {
                System.out.println(server);
                Registry registry = LocateRegistry.createRegistry(AppProperties.REGISTRY_PORT);
                System.out.println(registry);
                registry.bind(AppProperties.REGISTRY_NAME, new RemoteMethod());
                while (true) {
                    Socket socket = server.accept();
                    new ClientHandler(socket);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(Server.this, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        run.start();
    }

}
