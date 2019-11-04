package server;


import config.AppProperties;
import control.RoomController;
import dao.MessageDAO;
import dao.UserDAO;
import model.ClientHandler;
import rmi.RemoteMethod;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server extends JFrame {
    private static final RoomController roomController = new RoomController();
    private static MessageDAO messageDAO = null;
    private static UserDAO userDAO;
    private ServerSocket server;
    private Thread run;

    private Server() throws SQLException {
        messageDAO = new MessageDAO();
        userDAO = new UserDAO();
        roomController.loadChatRooms();
    }

    public static MessageDAO getMessageDAO() {
        return messageDAO;
    }

    public static void main(String args[]) {
        System.setProperty("java.rmi.server.hostname", AppProperties.HOST);
        try {
            Server server = new Server();
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static RoomController getRoomController() {
        return roomController;
    }

    public static UserDAO getUserDAO() {
        return userDAO;
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
