/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.models;

import iti.chat.common.ClientInter;
import iti.chat.common.Message;
import iti.chat.common.User;

/**
 *
 * @author Hasnaa Mohammed
 */
public class ClientImpl implements ClientInter{
    User user;
    Message msg;
    
    @Override
    public void recieveMessage(User sender) {
        System.out.println(msg.getContent());
    }
    
    public void setUser(User usr){
        user=usr;
    }
    @Override
    public User getUser() {
       return user;
    }
    
     public void setMessage(Message message){
        msg=message;
    }

    public Message getMessage() {
       return msg;
    }
}
