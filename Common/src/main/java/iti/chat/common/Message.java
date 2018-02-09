package iti.chat.common;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author nagib
 */
public class Message implements Serializable {

    private String content;
    private String color;
    private String font;
    private LocalDateTime time;

    /*variables added by Nagib  */


    /*variables added by Dina  */


    /*variables added by Hassna  */


    /*variables added by Hesham  */


    /*variables added by Fatma  */

    
    public Message(String content, String color, String font) {
        this.content = content;
        this.color = color;
        this.font = font;
        this.time = LocalDateTime.now();
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

    public void setTime(LocalDateTime time) {
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

    public LocalDateTime getTime() {
        return time;
    }

 
    /*Methods added by Nagib  */
    
    
 /*Methods added by Dina  */
    
    //
    
 /*Methods added by Hassna  */
    
    //
    
 /*Methods added by Hesham  */
    
    //
    
 /*Methods added by Fatma  */
    
    //
    

}
