package iti.chat.common;

import java.io.Serializable;


public class User implements Serializable{

    private String email;
    private String username;
    private String password;
    private int status;
    private int mode;
    private String fullname;
    private String gender;
    private String country;
    Message message ;
    
    /*variables added by Nagib  */


    /*variables added by Dina  */


    /*variables added by Hassna  */


    /*variables added by Hesham  */


    /*variables added by Fatma  */

  



    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setMessage(Message message) {
        this.message = message;
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getStatus() {
        return status;
    }

    public int getMode() {
        return mode;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }
    
    public Message getMessage() {
        return message;
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
