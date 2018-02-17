/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.models;

import client.controllers.ChatPageController;
import client.interfaces.FileTransferInt;
import iti.chat.common.ClientInter;
import iti.chat.common.Message;
import iti.chat.common.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Hasnaa Mohammed
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInter {

    private User user;
    private Message msg;
    private ChatPageController chatPageController = null;

    public ClientImpl() throws RemoteException {
        this.user = null;
    }

    public ClientImpl(User user) throws RemoteException {
        this.user = user;
    }

    @Override
    public void recieveMessage(ClientInter sender) throws RemoteException {
//        System.out.println(user.getMessage().getContent());
        System.out.println(user.getUsername() + " Has recievedMessage");
        chatPageController.recieveMessage(sender);
    }

    public void setUser(User usr) {
        user = usr;
    }

    @Override
    public User getUser() throws RemoteException {
        return this.user;
    }

    public void setMessage(Message message) {
        msg = message;
    }

    public Message getMessage() {
        return msg;
    }

    /*Methods added by Nagib  */
    @Override
    public Object getChatPageController() throws RemoteException {
        return this.chatPageController;
    }

    @Override
    public void setChatPageController(Object chatPageController) throws RemoteException {
        this.chatPageController=(ChatPageController)chatPageController;
    }

    //
    /*Methods added by Dina  */
    //
    //
    private FileTransferInt transferFile;

    public FileTransferInt getTransferFile() {
        return transferFile;
    }

    public void setTransferFile(FileTransferInt transferFile) {
        this.transferFile = transferFile;
    }
       
    //    
    /*Methods added by Hassna  */
    public boolean signOut() {
        boolean done = false;
        //serverObject.signOurServer(this.user.email);
        return done;
    }

    @Override
    public void getNotification(String content) throws RemoteException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*Methods added by Hesham  */
    //
    //
    /*Methods added by Fatma  */
    //
    //
    //
}
