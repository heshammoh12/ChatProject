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
import javafx.application.Platform;
import javafx.geometry.Pos;
import oracle.jdbc.OracleDriver;
import server.controllers.FXMLController;

/**
 *
 * @author Dina PC
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInter {

    FXMLController serverController = null;

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
        boolean isSompleted = signInServer(client.getUser().getEmail());
        if (isSompleted) {
            clientsArrayList.add(client);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    serverController.displayUsersLists();
                    try {
                        updateStatistics();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        notifyOnlineFriendsStatus(client, client.getUser().getEmail(), 2);

    }

    @Override
    public void unregisterClint(ClientInter client) throws RemoteException {
        boolean isSompleted = signOurServer(client.getUser().getEmail());
        clientsArrayList.remove(client);
        if (isSompleted) {
            clientsArrayList.remove(client);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    serverController.displayUsersLists();
                    try {
                        updateStatistics();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        notifyOnlineFriendsStatus(client, client.getUser().getEmail(), 3);
    }

    @Override
    public boolean signOurServer(String email) throws RemoteException {
        DBconnect conn;
        conn = DBconnect.getInstance();
        try {
            int updatedRows = conn.signOutdb(email);
            System.out.println("updatedRows ->" + updatedRows);
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

//
    /*Methods added by Nagib  */
    @Override
    public void acceptFriendRequest(String email1, String email2) throws RemoteException {
        DBconnect conn;
        int updatedRows = 0;
        try {
            conn = DBconnect.getInstance();
            updatedRows = conn.acceptFriendRequest(email1, email2);
            if (updatedRows > 0) {
                for (ClientInter clientInter : clientsArrayList) {
                    if (clientInter.getUser().getEmail().equals(email2)) {
                        System.out.println("Clint will be notified");                       
                        User friend = conn.getUserFriendsData(email1);
                        clientInter.appendNewFriend(friend);
                        clientInter.getNotification(friend.getFullname(), 4);
                    }

                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void rejectFriendRequest(String email1, String email2) throws RemoteException {
        System.out.println("server rejectFriendRequest"); 
        DBconnect conn;
        int updatedRows = 0;
        try {
            conn = DBconnect.getInstance();
            updatedRows = conn.rejectFriendRequest(email1, email2);

        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    @Override
    public ArrayList<User> getfriendRequests(String email) throws RemoteException {
        ArrayList<User> requesters = new ArrayList<>();
        DBconnect conn;
        System.out.println("gserver getfriendRequests");
        try {
            conn = DBconnect.getInstance();
            ArrayList<String> frindEmails = conn.getRequests(email);
            

            for (String frindEmail : frindEmails) {
                System.out.println(frindEmail);
                User friend = conn.getUserFriendsData(frindEmail);
                requesters.add(friend);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requesters;
    }

    @Override
    public ClientInter getFriendClient(String mail) throws RemoteException {
        ClientInter friendClient = null;
        for (ClientInter clientInter : clientsArrayList) {
            if (clientInter.getUser().getEmail().equals(mail)) {
                friendClient = clientInter;
            }
        }
        return friendClient;

    }

    @Override
    public Object getServerController() throws RemoteException {
        return this.serverController;
    }

    @Override
    public void setServerController(Object serverController) throws RemoteException {
        this.serverController = (FXMLController) serverController;
    }

    @Override
    public void updateStatistics() throws RemoteException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("updateStatistics()");
                serverController.showStatistics();

            }
        });
    }

    @Override
    public void clearClientsList() throws RemoteException {
        clientsArrayList.clear();
    }

    @Override
    public boolean signInServer(String email) throws RemoteException {
        DBconnect conn;
        conn = DBconnect.getInstance();
        try {
            int updatedRows = conn.signIndb(email);
            System.out.println("updatedRows ->" + updatedRows);
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void notifyOnlineFriendsStatus(ClientInter client, String mail, int state) {
        ArrayList<String> mails = null;
        ArrayList<ClientInter> onlineClients = new ArrayList<>();
        DBconnect conn;
        try {
            conn = DBconnect.getInstance();
            mails = conn.getOnlineFriends(mail);
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (String mail1 : mails) {
                ClientInter c_i = getFriendClient(mail1);
                if (c_i != null) {
                    onlineClients.add(c_i);
                }
            }

            for (ClientInter onlineClient : onlineClients) {
                onlineClient.friendChangeState(client, state);
                System.out.println(state);
                if(state ==2){
                
                onlineClient.getNotification(client.getUser().getFullname(), 2);
                }
                else onlineClient.getNotification(client.getUser().getFullname(), 3);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("finish notfiing");

    }

    public void notifyOnlineFriendsMode(ClientInter client, String mail, int mode) {
        ArrayList<String> mails = null;
        ArrayList<ClientInter> onlineClients = new ArrayList<>();
        DBconnect conn;
        try {
            conn = DBconnect.getInstance();
            mails = conn.getOnlineFriends(mail);
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (String mail1 : mails) {
                ClientInter c_i = getFriendClient(mail1);
                if (c_i != null) {
                    onlineClients.add(c_i);
                }
            }

            for (ClientInter onlineClient : onlineClients) {
                onlineClient.friendChangeMode(client, mode);
                onlineClient.getNotification(client.getUser().getFullname(), 3);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("finish notfiing");

    }

    //
    //
    /*Methods added by Dina  */
    //
    @Override
    public void sendAnnoncment(String content) {
        System.out.println("annoncment content is " + content);
        try {
            clientsArrayList.forEach((client) -> {
                try {
                    client.getAnnoncment(content);
                } catch (RemoteException ex) {
                    Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void notifyFriendRequest(ClientInter reciever) {

    }

    //
    /*Methods added by Hassna  */
    //
    //
    /*Methods added by Hesham  */
    //
    @Override
    public ArrayList<User> search(String email1, String email2) throws RemoteException {
        ArrayList<User> names = new ArrayList<>();

        DBconnect conn;
        try {
            conn = DBconnect.getInstance();
            boolean isrequestExist = conn.requestExist(email1, email2);
            if (!isrequestExist) {
                names = conn.getUsersByName(email2);

                for (User frindEmail : names) {
                    System.out.println("foreach" + frindEmail.getEmail());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return names;
    }

    @Override
    public int updateMode(ClientInter client, int mode, String email) throws RemoteException {
        DBconnect conn;
        conn = DBconnect.getInstance();
        int rowAffected = 0;
        System.out.println("server imp " + mode + "  " + email);
        switch (mode) {
            case 1:
                rowAffected = conn.updateUserMode(1, email);
                notifyOnlineFriendsMode(client, email, 1);
                break;
            case 2:
                rowAffected = conn.updateUserMode(2, email);
                notifyOnlineFriendsMode(client, email, 2);
                break;
            case 3:
                rowAffected = conn.updateUserMode(3, email);
                notifyOnlineFriendsMode(client, email, 3);
                break;

        }

        return rowAffected;
    }

    @Override
    public int addFriend(String sender, String reciever) throws RemoteException {
        DBconnect conn;
        conn = DBconnect.getInstance();
        int rowAffected = 0;

        try {
            rowAffected = conn.addFriendRequest(sender, reciever);
             if (rowAffected > 0) {
                for (ClientInter clientInter : clientsArrayList) {
                    if (clientInter.getUser().getEmail().equals(reciever)) {
                        System.out.println("Clint will be notified");                       
                        User friend = conn.getUserFriendsData(sender);
                        clientInter.appendNewFriendRequest(friend);
                        clientInter.getNotification(friend.getFullname(), 5);
                    }

                }
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowAffected;
    }
    //
    /*Methods added by Fatma  */
    //
    //

}
