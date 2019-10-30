package client;

import control.MessageController;
import model.Message;

public class Main extends javax.swing.JFrame {

    private Thread th;
    private int currentID = 0;

    public Main() {
    }

    public static void main(String args[]) {
        new Main().start();
    }

    private void start() {
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("waiting message ...");
                        Message ms = (Message) MessageController.getIn().readObject();
                        System.out.println(ms);
                        String status = ms.getStatus();
                        if (status.equals("Message")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("New")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("Photo")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("File")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("Error")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("Emoji")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("GetFile")) {
                            getMessage(ms.getID(), ms.getMessage());
                        } else if (status.equals("Sound")) {
                            getMessage(ms.getID(), ms.getMessage());
                        }
                    }
                } catch (Exception e) {
                    String ms = e.getMessage();
                    if (ms.equals("Socket closed")) {
//                        signOut("Sign out");
                    } else if (ms.equals("Connection reset")) {
//                        signOut("Server has error");
                    } else {
//                        signOut("Error : " + ms);
                    }

                }
            }
        });
        th.start();
    }


    private void getMessage(int ID, String ms) {
        System.out.println("Message " + ms);
        System.out.println("ID" + ID);
    }

}
