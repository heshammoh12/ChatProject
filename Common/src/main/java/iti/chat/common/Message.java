package iti.chat.common;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author nagib
 */
public class Message implements Serializable {

    private String content;
    private String color;
    private String font;
    private LocalDate time;

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

    public void setContent(String content) {
        this.content = content;
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
    //
    /*Methods added by Fatma  */
    //
    
    
}
