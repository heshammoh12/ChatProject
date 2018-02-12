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
        receiver.recieveMessage(sender.getUser());
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
        return clientsArrayList.get(0);
    }
    //
    //
    /*Methods added by Dina  */
    //
    //
    /*Methods added by Hassna  */
    //
    //
    /*Methods added by Hesham  */
    //
    //
    /*Methods added by Fatma  */
    //
    //

}
