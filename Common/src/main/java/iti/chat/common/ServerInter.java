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
    public ClientInter getFriendClient(String mail) throws RemoteException;
    public Object getServerController() throws RemoteException;
    public void setServerController(Object serverController) throws RemoteException;
    public void updateStatistics() throws RemoteException;
    public void clearClientsList()throws RemoteException;
    public boolean signInServer(String email) throws RemoteException;

    //
    
   /*Methods added by Dina  */
    
    //
    public void sendAnnoncment(String content)throws RemoteException;
    public void notifyFriendRequest(ClientInter reciever);
   /*Methods added by Hassna  */
    
    public boolean signOurServer(String email) throws RemoteException;
    
   /*Methods added by Hesham  */
    public ArrayList<User> search(String name) throws RemoteException;
    
    public int updateMode(String mode , String email)throws RemoteException;
    
    public int addFriend(String sender,String reciever)throws RemoteException;
    //
    
   /*Methods added by Fatma  */
    
    //
    
}
