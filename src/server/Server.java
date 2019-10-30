package server;


import config.AppProperties;
import control.RoomController;
import model.Client;
import rmi.RemoteMethod;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server extends JFrame {
    private RoomController roomController = new RoomController();
    private ServerSocket server;
    private Thread run;

    public Server() {
        roomController.createChatRoom("chat room 0");
        roomController.createChatRoom("chat room 1");
        roomController.createChatRoom("chat room 2");
    }

    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public RoomController getRoomController() {
        return roomController;
    }

    public void setRoomController(RoomController roomController) {
        this.roomController = roomController;
    }

    private void startServer() throws Exception {
        server = new ServerSocket(AppProperties.PORT);
        run = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(server);
                    Registry registry = LocateRegistry.createRegistry(AppProperties.REGISTRY_PORT);
                    System.out.println(registry);
                    registry.bind(AppProperties.REGISTRY_NAME, new RemoteMethod());
                    while (true) {
                        Socket socket = server.accept();
                        new Client(socket);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(Server.this, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        run.start();
    }

    private void stopServer() throws Exception {
        System.out.println(run);
        System.out.println(server);
        run.interrupt();
        server.close();
    }
}
