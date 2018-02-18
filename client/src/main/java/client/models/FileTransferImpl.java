/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.models;

import client.controllers.ChatBoxController;
import iti.chat.common.FileTransferInt;
import iti.chat.common.ClientInter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dina PC
 */
public class FileTransferImpl implements FileTransferInt ,Serializable{

    @Override
    public void sendFile(ClientInter receiver, File f) {
       FileInputStream in=null;
       boolean acceptTransfer = true;
       if(acceptTransfer)
       {
           try {
               in = new FileInputStream(f);
               byte[] data = new byte[1024 * 1024];
               int dataLength = in.read(data);
               boolean append = false;
               System.out.println("in recieve file function --dina method");
                while (dataLength > 0) {
                    receiver.getTransferFile().recieveFile(f.getName(), append, data, dataLength);
                    dataLength = in.read(data);
                    append = true;
                }
           } catch (FileNotFoundException ex) {
               Logger.getLogger(FileTransferImpl.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(FileTransferImpl.class.getName()).log(Level.SEVERE, null, ex);
           }       
       }
    }

    @Override
    public void recieveFile(String filename, boolean append, byte[] data, int dataLength) {
       String savePath = "C:\\Users\\Public\\Downloads\\";
       File f = new File(savePath+filename);
        try {
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, append);
            out.write(data, 0, dataLength);
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(FileTransferImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
