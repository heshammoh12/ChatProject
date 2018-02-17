/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.interfaces;

import client.models.ClientImpl;
import java.io.File;

/**
 *
 * @author Dina PC
 */
public interface FileTransferInt {
    
    public void sendFile(ClientImpl receiver , File f );
    public void recieveFile(String filename,boolean append, byte[] data, int dataLength);
    public boolean askForAcceptance();
}
