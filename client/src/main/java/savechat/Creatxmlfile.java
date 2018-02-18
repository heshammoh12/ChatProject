/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savechat;

import iti.chat.common.Message;
import iti.chat.common.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author fatma
 */
public class Creatxmlfile {
    
    
     public static void createFile(File file, ArrayList<Message> messages, User user) throws FileNotFoundException, IOException {
        try {

            JAXBContext context = JAXBContext.newInstance("savechat");
            ObjectFactory factory = new ObjectFactory();
            ChathistoryType history = factory.createChathistoryType();
            history.setHolder(user.getUsername());
            List<MsgType> messagesCollection = history.getMsg();
       

            for (Message message : messages) {
                MsgType messageType = factory.createMsgType();
                messageType.setBody(message.getContent());
                messageType.setFontColor(message.getColor());
                try {
                    LocalDate date = message.getTime();

                    GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
                    XMLGregorianCalendar xcal;

                    xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

                    xcal.toGregorianCalendar().toZonedDateTime().toLocalDate();
                    messageType.setDate(xcal);
//Converting back is simpler:
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(Creatxmlfile.class.getName()).log(Level.SEVERE, null, ex);
                }

                messageType.setFrom(message.getFrom());
                messageType.setFontFamily(message.getFont());
                List arrlist2 = message.getListoftos();
                List tos = messageType.getTo();
                tos.addAll(arrlist2);
                for (int j = 0; j < tos.size(); j++) {
                    tos.get(j).toString();
                }
               
                messageType.setFontSize(BigInteger.valueOf(15));

                messagesCollection.add(messageType);

            }

            JAXBElement<ChathistoryType> createChathistory = factory.createChathistory(history);

            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //************* put Schcema
            marsh.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "XmlSchema.xsd");
            //***********put XLST ************
            marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders",
                    "<?xml-stylesheet type='text/xsl' href='test3.xsl'?>");

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            marsh.marshal(createChathistory, fileOutputStream);
             TransformerFactory tFactory=TransformerFactory.newInstance();

        Source xslDoc=new StreamSource("E:\\JavaProject\\ChatProject\\client\\src\\main\\java\\savechat\\test3.xsl");
        Source xmlDoc=new StreamSource(file);

        String outputFileName=""+file.getPath()+"result"+".html";

        OutputStream htmlFile=new FileOutputStream(outputFileName);
        Transformer trasform=tFactory.newTransformer(xslDoc);
            try {
                trasform.transform(xmlDoc, new StreamResult(htmlFile));
            } catch (TransformerException ex) {
                Logger.getLogger(Creatxmlfile.class.getName()).log(Level.SEVERE, null, ex);
            }
            fileOutputStream.close();

        } catch (JAXBException ex) {
            ex.printStackTrace();

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Creatxmlfile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
}
