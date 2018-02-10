/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.models;

import iti.chat.common.LogInVerificationInter;
import iti.chat.common.User;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnaa Mohammed
 */
public class LogInVerificationImpl implements LogInVerificationInter{

    @Override
    public User login(String email, String password) throws RemoteException {
       User user = new User();
       DBconnect dbconn = DBconnect.getInstance();
        try {
            user= dbconn.getUserData(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(LogInVerificationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
       return user;
    }
    
}
