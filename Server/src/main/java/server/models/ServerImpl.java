package server.models;

import iti.chat.common.ClientInter;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author Dina PC
 */
public class ServerImpl implements ServerInter {
    @Override
    public ArrayList<User> getFrinds(String email) throws RemoteException {
        ArrayList<User> friendsNames = null;
        try {
            DBconnect conn = DBconnect.getInstance();
            ResultSet rs = conn.getUserFriends(email);
            friendsNames = new ArrayList<>();
            while (rs.next()) {
                String friendEmail = rs.getString(1);
                /*new query to get user friend's data*/
                
                ResultSet result = conn.getUserFriendsData(friendEmail);
                /*create a new user object,set the friends data on it and add it to the arraylist*/
                String fEmail = result.getString(1);
                String fName = result.getString(2);
                String fgender = result.getString(3);
                String fCountry = result.getString(4);
                int fStatus = result.getInt(5);
                int fMode = result.getInt(6);
                User friend = new User(fEmail, fName, fgender, fCountry, fStatus,fMode);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unregisterClint(ClientInter client) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
