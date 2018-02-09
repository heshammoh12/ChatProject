package iti.chat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SignUpVerificationInter  extends Remote  {

    boolean emailExists(String email)throws RemoteException;

    boolean insertUser(User user)throws RemoteException;
    

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
