package iti.chat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInter extends Remote {

    public ArrayList<User> getFrinds(String email) throws RemoteException;

    public void sendMessage(ClientInter sender, ClientInter receiver) throws RemoteException;

    public void registerClint(ClientInter client) throws RemoteException;

    public void unregisterClint(ClientInter client) throws RemoteException;
    
   /*Methods added by Nagib  */
    
    //
    
   /*Methods added by Dina  */
    
    //
    
   /*Methods added by Hassna  */
    
    //
    
   /*Methods added by Hesham  */
    
    //
    
   /*Methods added by Fatma  */
    
    //
    
}
