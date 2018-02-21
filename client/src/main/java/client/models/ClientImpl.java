/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.models;

import client.controllers.ChatPageController;
import iti.chat.common.FileTransferInt;
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
        this.chatPageController = (ChatPageController) chatPageController;
    }

    @Override
    public void acceptRecieveingFile(ClientInter sender, String tabid) throws RemoteException {
        System.out.println("clint acceptRecieveingFile user name " + this.getUser().getFullname());
        System.out.println("sender acceptRecieveingFile user name " + sender.getUser().getFullname());
        System.out.println(user.getUsername() + "this clint will chooce to recieve file or not ");
        chatPageController.recievefile(sender, tabid);
    }

    @Override
    public void startSendingFile(String tabid) throws RemoteException {
        chatPageController.startSendingFile(tabid);
    }

    @Override
    public void friendChangeState(ClientInter client,int state) throws RemoteException {
        System.out.println("friendBecameonline ClintImp");
        chatPageController.friendChangeState(client , state);
    }

    @Override
    public void friendChangeMode(ClientInter client,int mode) throws RemoteException {
        System.out.println("friendBecameonline ClintImp");
        chatPageController.friendChangeMode(client , mode);
    }
    
    @Override
    public void appendNewFriend(User newFriend) throws RemoteException {
        chatPageController.appendNewFriend( newFriend);
    }
    @Override
    public void appendNewFriendRequest(User newFriend) throws RemoteException {
        chatPageController.appendNewFriendRequest( newFriend);
    }
    //
    /*Methods added by Dina  */
    //
    //
    private FileTransferInt transferFile = new FileTransferImpl();

    @Override
    public FileTransferInt getTransferFile() {
        return transferFile;
    }

    @Override
    public void setTransferFile(FileTransferInt transferFile) {
        this.transferFile = transferFile;
    }
    @Override
    public void getAnnoncment(String Content){
        System.out.println("in client impl get annoncment"+Content);
        chatPageController.getAnnoncment(Content);
    }
    //    
    /*Methods added by Hassna  */
    public boolean signOut() {
        boolean done = false;
        //serverObject.signOurServer(this.user.email);
        return done;
    }

    @Override
    public void getNotification(String content,int type) throws RemoteException {
        chatPageController.getNotification(content,type);
    }

    /*Methods added by Hesham  */
    //
    //
    /*Methods added by Fatma  */
    //
    //
    //
}
