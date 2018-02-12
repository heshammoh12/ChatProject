/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.models;

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

    User user;
    Message msg;

    public ClientImpl() throws RemoteException {
        this.user = null;
    }

    public ClientImpl(User user) throws RemoteException {
        this.user = user;
    }

    @Override
    public void recieveMessage(User sender) throws RemoteException {
        System.out.println(msg.getContent());
    }

    public void setUser(User usr) {
        user = usr;
    }

    @Override
    public User getUser() throws RemoteException {
        return user;
    }

    public void setMessage(Message message) {
        msg = message;
    }

    public Message getMessage() {
        return msg;
    }

    /*Methods added by Nagib  */
 /*Methods added by Dina  */
//    
    /*Methods added by Hassna  */
    //
    /*Methods added by Hesham  */
    //
    /*Methods added by Fatma  */
    //
}
