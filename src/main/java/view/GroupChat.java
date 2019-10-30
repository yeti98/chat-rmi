/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.MessageController;
import model.ChatRoom;
import model.Message;
import model.User;
import rmi.IRMI;

import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;

/**
 * @author Thanh
 */
public class GroupChat extends javax.swing.JFrame {

    private final IRMI rmi;
    /**
     * Creates new form NewJFrame3
     */
    private User user;
    private DefaultTableModel tblChatModel;
    private DefaultTableModel tblUserModel;
    private Thread thread;
    private ChatRoom chatRoom;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExitButton;
    private javax.swing.JButton SentButton;
    private javax.swing.JTable tblUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblChat;


    public GroupChat(User user, ChatRoom chatRoom, IRMI rmi) {
        initComponents();
        this.user = user;
        this.tblChatModel = (DefaultTableModel) tblChat.getModel();
        this.tblUserModel = (DefaultTableModel) tblUser.getModel();
        this.chatRoom = chatRoom;
        this.rmi = rmi;
        refreshStatus();
        jLabel1.setText(chatRoom.getName());
        runThread();
    }

    public static void main(User user, ChatRoom chatRoom, IRMI rmi) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GroupChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GroupChat(user, chatRoom, rmi).setVisible(true);
            }
        });
    }
    private void deleteUserTable(){
        int rowCount = tblUserModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tblUserModel.removeRow(i);
        }
    }
    private void refreshStatus() {
        deleteUserTable();
        try {
            for (User usr : chatRoom.getMembers()) {
                String status = rmi.checkUserStatus(chatRoom.getId(), usr.getId());
                tblUserModel.addRow(new Object[]{usr.getUsername(), status});
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void runThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("waiting message ...");
                        MessageController messageController = chatRoom.getMessageController();
                        Message ms = (Message) messageController.getIn().readObject();
                        System.out.println(ms);
                        String status = ms.getStatus();
                        if (status.equals("Message")) {
                            addMessage(ms);
                        } else if (status.equals("New")) {
                            refreshStatus();
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
        thread.start();
    }

    private void getMessage(int ID, String ms) {
    }

    private void addMessage(Message ms) {
        tblChatModel.addRow(new Object[]{ms.getUser().getUsername(), ms.getMessage(), ms.getCreateAt()});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChat = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        SentButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblChat.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "User", "Nội dung", "Thời gian"
                }
        ));
        jScrollPane1.setViewportView(tblChat);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        SentButton.setText("Gửi");
        SentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SentButtonActionPerformed(evt);
            }
        });

        ExitButton.setText("Exit Group Chat");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "User", "Status"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblUser);
        if (tblUser.getColumnModel().getColumnCount() > 0) {
            tblUser.getColumnModel().getColumn(0).setResizable(true);
        }

        jLabel2.setText("Group 311C9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(23, 23, 23)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(87, 87, 87)
                                                                                                .addComponent(ExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(54, 54, 54)
                                                                                .addComponent(SentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(54, 54, 54)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(45, 45, 45)
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(ExitButton)
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(SentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void SentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SentButtonActionPerformed
        // TODO add your handling code here:
        String text = jTextField1.getText();
        System.out.println(text);
        try {
            Message message = new Message();
            message.setUser(user);
            message.setMessage(text);
            MessageController messageController = chatRoom.getMessageController();
            messageController.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_SentButtonActionPerformed
    // End of variables declaration//GEN-END:variables
}
