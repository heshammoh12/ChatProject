package iti.chat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author nagib
 */
public interface ClientInter extends Remote {

    public void recieveMessage(ClientInter sender) throws RemoteException;

    /*Methods added by Nagib  */
    public Object getChatPageController() throws RemoteException;
    public void acceptRecieveingFile(ClientInter sender,String tabid) throws RemoteException ;
    public void startSendingFile(String tabid) throws RemoteException ;

    public void setChatPageController(Object chatPageController) throws RemoteException;
    public void friendChangeState(ClientInter client,int state) throws RemoteException;
    public void friendChangeMode(ClientInter client,int mode) throws RemoteException;

    //
    /*Methods added by Dina  */
    public User getUser() throws RemoteException;
    public FileTransferInt getTransferFile() throws RemoteException;
    public void setTransferFile(FileTransferInt transferFile) throws RemoteException;

    /*Methods added by Hassna  */
    public void getNotification(String content) throws RemoteException;

    /*Methods added by Hesham  */
    //
    //
    /*Methods added by Fatma  */
    //
}
