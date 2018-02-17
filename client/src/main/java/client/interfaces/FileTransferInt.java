
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
