
package iti.chat.common;

import java.io.File;

/**
 *
 * @author Dina PC
 */
public interface FileTransferInt {
    
    public void sendFile(ClientInter receiver , File f );
    public void recieveFile(String filename,boolean append, byte[] data, int dataLength);
}
