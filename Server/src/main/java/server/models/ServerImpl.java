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

    ResultSet rs;
    Connection con;

    public ServerImpl() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "chat", "chat");
        con.setAutoCommit(false);
    }

    @Override
    public ArrayList<User> getFrinds(String email) throws RemoteException {
        PreparedStatement pst;
        ArrayList<User> friendsNames = null;
        try {
            pst = con.prepareStatement("select friendemail from userfriends where email = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, email);
            rs = pst.executeQuery();
            friendsNames = new ArrayList<>();
            if (rs.next()) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return friendsNames;
    }

    @Override
    public void sendMessage(ClientInter sender, ClientInter receiver) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
