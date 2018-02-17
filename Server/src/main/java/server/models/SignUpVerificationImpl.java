/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.models;

import iti.chat.common.ServerInter;
import iti.chat.common.SignUpVerificationInter;
import iti.chat.common.User;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Hasnaa Mohammed
 */
public class SignUpVerificationImpl extends UnicastRemoteObject implements SignUpVerificationInter {
    ServerInter server = null;
    Registry registry = null;
    public SignUpVerificationImpl() throws RemoteException {
        System.out.println("SignUpVerificationImpl");
    }

    @Override
    public boolean emailExists(String email) throws RemoteException {
        boolean exist = false;
        DBconnect dbconn = DBconnect.getInstance();
        try {
            exist = dbconn.checkEmail(email);
        } catch (SQLException ex) {
            Logger.getLogger(SignUpVerificationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;
    }

    @Override
    public boolean insertUser(User user) throws RemoteException {
        int inserted1 = 0;
        int inserted2 = 0;
        DBconnect dbconn = DBconnect.getInstance();

        try {
            inserted1 = dbconn.insertToUserInfo(user.getEmail(), user.getFullname(), user.getGender(), user.getCountry());
            inserted2 = dbconn.insertToUserLogIn(user.getEmail(), user.getUsername(), user.getPassword(), user.getStatus(), user.getMode());
        } catch (SQLException ex) {
            Logger.getLogger(SignUpVerificationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean isInserted =(inserted1 > 0) && (inserted2 > 0);
        if(isInserted){}
        return isInserted;
    }
    
    private void seviceLookUp() {
        try {
            registry= LocateRegistry.getRegistry(2000);
            server=(ServerInter) registry.lookup("ChatService");
            
            Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                server.updateStatistics();
                            } catch (RemoteException ex) {
                                Logger.getLogger(SignUpVerificationImpl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
        } catch (NotBoundException | RemoteException ex) {
            Logger.getLogger(SignUpVerificationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
