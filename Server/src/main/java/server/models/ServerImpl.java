package server.models;

import iti.chat.common.ClientInter;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author Dina PC
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInter {

    static ArrayList<ClientInter> clientsArrayList = new ArrayList<ClientInter>();

    public ServerImpl() throws RemoteException {
        System.out.println("ServerImpl");
    }

    @Override
    public ArrayList<User> getFrinds(String email) throws RemoteException {
        ArrayList<User> friendsNames = null;
        DBconnect conn;
        try {
            conn = DBconnect.getInstance();
            ArrayList<String> frindEmails = conn.getUserFriends(email);
            friendsNames = new ArrayList<>();
            for (String frindEmail : frindEmails) {
                User friend = conn.getUserFriendsData(frindEmail);
                friendsNames.add(friend);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return friendsNames;
    }

    @Override
    public void sendMessage(ClientInter sender, ClientInter receiver) throws RemoteException {
        receiver.recieveMessage(sender);
    }

    @Override
    public void registerClint(ClientInter client) throws RemoteException {
        clientsArrayList.add(client);
    }

    @Override
    public void unregisterClint(ClientInter client) throws RemoteException {
        clientsArrayList.remove(client);
    }

    @Override
    public boolean signOurServer(String email) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//
    /*Methods added by Nagib  */
    
   @Override
    public ClientInter getFriendClient(String mail) throws RemoteException {
        ClientInter friendClient= null;
        for (ClientInter clientInter : clientsArrayList) {
            if(clientInter.getUser().getEmail().equals(mail))
                friendClient = clientInter;
        }
        return friendClient;
        
    }  
    
    //
    //
    /*Methods added by Dina  */
    //
        @Override
    public void sendNotification(String content) {
            System.out.println("annoncment content is "+content);
                try {  
                    clientsArrayList.forEach((client)->{
                        try {
                            client.getNotification(content);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            
    }
    //
    /*Methods added by Hassna  */
    //
    //
    /*Methods added by Hesham  */
    //
        @Override
    public ArrayList<User> search(String name) throws RemoteException 
    {
        ArrayList<User> names = null;
        DBconnect conn;
        try {
            conn = DBconnect.getInstance();
            names = conn.getUsersByName(name);
            
            for (User frindEmail : names) {
                System.out.println("foreach"+frindEmail.getEmail());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return names;
    }
    //
    /*Methods added by Fatma  */
    //
    //



}
