/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.models;

import client.interfaces.FileTransferInt;
import iti.chat.common.ClientInter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dina PC
 */
public class FileTransferImpl implements FileTransferInt {

    @Override
    public void sendFile(ClientImpl receiver, File f) {
       FileInputStream in=null;
       boolean acceptTransfer = receiver.getTransferFile().askForAcceptance();
       if(acceptTransfer)
       {
           try {
               in = new FileInputStream(f);
               byte[] data = new byte[1024 * 1024];
               int dataLength = in.read(data);
               boolean append = false;
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
       String savePath = "Downloads";
       File f = new File(savePath);
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
    @Override
    public boolean askForAcceptance() {
        return true;
    }

    
}
