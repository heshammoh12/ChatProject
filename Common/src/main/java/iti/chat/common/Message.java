
package iti.chat.common;

import java.io.Serializable;
import java.time.LocalDate;
import javafx.scene.paint.Color;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nagib
 */
public class Message implements Serializable {

    private String content;
    private String color;
    private String font;
    private LocalDate time;
    private String fullName;
    private String fontSize;
    private List<String> listoftos;
    private String from;
    

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }
    /*variables added by Nagib  */
    private String tabId;
            

    /*variables added by Dina  */
    

    /*variables added by Hassna  */


    /*variables added by Hesham  */


    /*variables added by Fatma  */

    
    public Message(String content, String color, String font) {
        this.content = content;
        this.color = color;
        this.font = font;
        this.time = LocalDate.now();
    }


    public void setColor(String color) {
        this.color = color;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getColor() {
        return color;
    }

    public String getFont() {
        return font;
    }

    public LocalDate getTime() {
        return time;
    }

 
    /*Methods added by Nagib  */
    public String getTabId() {
        return tabId;
    }
    public void setTabId(String tabId) {
        this.tabId = tabId;
    }
    public Message(String content, String color, String font,String tabId) {
        this.content = content;
        this.color = color;
        this.font = font;
        this.time = LocalDate.now();
            this.tabId=tabId;
    }

    /*Methods added by Dina  */
    //
    /*Methods added by Hassna  */
    //
    /*Methods added by Hesham  */

    public Message(String content, String fullName, String tabId, String color, String font, LocalDate time, String fontSize) {
        this.content = content;
        this.color = color;
        this.font = font;
        this.time = time;
        this.fullName = fullName;
        this.fontSize = fontSize;
        this.tabId = tabId;
    }
           public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    
     public List<String> getListoftos() {
        return listoftos;
    }

    public void setListoftos(ArrayList<String> listoftos) {
        this.listoftos = listoftos;
    }
    
    //
    /*Methods added by Fatma  */
     public Message(String content, String color, String font, LocalDate time, String tabId, List<String> listoftos, String from, BigInteger fontsize) {
        this.content = content;
        this.color = color;
        this.font = font;
        this.time = time;
        this.tabId = tabId;
        this.listoftos = listoftos;
        this.from = from;
      //  this.fontsize = fontsize;
    }

    //
    
    
}
