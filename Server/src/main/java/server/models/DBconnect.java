/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.models;

import iti.chat.common.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author Hasnaa Mohammed
 */
public class DBconnect {
        private static DBconnect jdbc;  
           
     //JDBCSingleton prevents the instantiation from any other class.  
       private DBconnect() {  }  
        
    //Now we are providing gloabal point of access.  
         public static DBconnect getInstance() {    
                                     if (jdbc==null)  
                                   {  
                                            jdbc=new  DBconnect();  
                                   }  
                         return jdbc;  
             }  
            
   // to get the connection from methods like insert, view etc.   
          private static Connection getConnection()throws ClassNotFoundException, SQLException 
          {  
                
              Connection con=null;  
            DriverManager.registerDriver(new OracleDriver());
            try {  
                con= DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","chat","chat");
            } catch (SQLException ex) {
                con= DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","chat","chat");
            }
              return con;  
                
          }  
            
 //to insert the record into the database   
          public int insertToUserInfo(String email, String fullname, String gender, String country) throws SQLException  
          {  
              Connection c=null;  
                
              PreparedStatement ps=null;  
                
              int recordCounter=0;  
                
              try {  
                    
                      c=this.getConnection();  
                      ps=c.prepareStatement("INSERT INTO USERINFO(EMAIL, FULLNAME, GENDER, COUNTRY) VALUES (?,?,?,?)");
                      ps.setString(1, email);  
                      ps.setString(2, fullname); 
                      ps.setString(3, gender);  
                      ps.setString(4, country); 
                      recordCounter=ps.executeUpdate();  
                        
              } catch (Exception e) { e.printStackTrace(); } finally{  
                    if (ps!=null){  
                      ps.close();  
                  }if(c!=null){  
                      c.close();  
                  }   
              }  
             return recordCounter;  
          }  
    public int insertToUserLogIn(String email, String username, String password, int state, int mode) throws SQLException  
          {  
              Connection c=null;  
                
              PreparedStatement ps=null;  
                
              int recordCounter=0;  
                
              try {  
                    
                      c=this.getConnection();  
                      ps=c.prepareStatement("INSERT INTO USERLOGIN(EMAIL, USERNAME, PASSWORD, USERSTATUS, USERMODE) VALUES (?,?,?,?,?)");
                      ps.setString(1, email);  
                      ps.setString(2, username); 
                      ps.setString(3, password);  
                      ps.setInt(4, state);
                      ps.setInt(5, mode);
                      recordCounter=ps.executeUpdate();  
                        
              } catch (Exception e) { e.printStackTrace(); } finally{  
                    if (ps!=null){  
                      ps.close();  
                  }if(c!=null){  
                      c.close();  
                  }   
              }  
             return recordCounter;  
          }
    
       
    public int addFriendRequest(String senderEmail, String recieverEmail ) throws SQLException  
          {  
              Connection c=null;  
                
              PreparedStatement ps=null;  
                
              int recordCounter=0;  
                
              try {  
                    
                      c=this.getConnection();  
                      ps=c.prepareStatement("INSERT INTO FRIENDSREQUESTS(EMAIL, MYFRIENDEMAIL) VALUES (?,?)");
                      ps.setString(1, senderEmail);  
                      ps.setString(2, recieverEmail); 
                      
                      recordCounter=ps.executeUpdate();  
                        
              } catch (Exception e) { e.printStackTrace(); } finally{  
                    if (ps!=null){  
                      ps.close();  
                  }if(c!=null){  
                      c.close();  
                  }   
              }  
             return recordCounter;  
          }
    
    public int acceptFriendRequest(String senderEmail, String recieverEmail ) throws SQLException  
          {  
              Connection c=null;  
                
              PreparedStatement ps=null;  
               PreparedStatement ps2=null; 
              int recordCounter=0;  
               int recordCounter2=0;  
              try {  
                    
                      c=this.getConnection();  
                      ps=c.prepareStatement("INSERT INTO USERFRIENDS(EMAIL, FRIENDEMAIL) VALUES (?,?)");
                      ps.setString(1, senderEmail);  
                      ps.setString(2, recieverEmail); 
                      
                      recordCounter=ps.executeUpdate();  
                      
                      ps2=c.prepareStatement(" delete from FRIENDSREQUESTS where EMAIL=? AND MYFRIENDEMAIL=?");
                      ps2.setString(1, senderEmail);  
                      ps2.setString(2, recieverEmail);
                      recordCounter2=ps2.executeUpdate();
                      
              } catch (Exception e) { e.printStackTrace(); } finally{  
                    if (ps!=null){  
                      ps.close();  
                  }if(c!=null){  
                      c.close();  
                  }   
              }  
             return recordCounter;  
          }
    
//to view the data from the database        
      public  boolean checkEmail(String email) throws SQLException  
      {  
          boolean exist=false;
                Connection con = null;  
        PreparedStatement ps = null;  
        ResultSet rs = null;  
                  
                try {  
                      
                        con=this.getConnection();  
                        ps=con.prepareStatement("select EMAIL from USERLOGIN WHERE EMAIL=?");  
                        ps.setString(1, email);  
                        rs=ps.executeQuery();  
                        if(rs.next()){
				exist= true;
			}else{
				exist= false;
			} 
                
          } catch (Exception e) { System.out.println(e);}  
          finally{  
                    if(rs!=null){  
                        rs.close();  
                    }if (ps!=null){  
                      ps.close();  
                  }if(con!=null){  
                      con.close();  
                  }   
                } 
                return exist;
      }
      
      //to view the data from the database        
      public  User getUserData(String email,String password) throws SQLException  
      {  
          User user = new User();
          boolean exist=false;
                Connection con = null;  
        PreparedStatement ps = null;  
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
                  
                try {  
                      
                        con=this.getConnection();  
                        ps=con.prepareStatement("select * from USERLOGIN WHERE EMAIL =? AND PASSWORD =?");
                        ps.setString(1, email); 
                        ps.setString(2, password);
                        rs=ps.executeQuery();
                        if(rs.next()){
				exist= true;
                                user.setEmail(rs.getString("EMAIL"));
                                user.setMode(rs.getInt("USERMODE"));
                                user.setPassword(rs.getString("PASSWORD"));
                                user.setUsername(rs.getString("USERNAME"));
                                user.setStatus(rs.getInt("USERSTATUS"));
                                
                                ps2=con.prepareStatement("Select * from USERINFO WHERE EMAIL =?");
                                ps2.setString(1, email);
                                rs2=ps2.executeQuery();
                                if(rs2.next()){
                                    user.setCountry(rs2.getString("COUNTRY"));
                                    user.setFullname(rs2.getString("FULLNAME"));
                                    user.setGender(rs2.getString("GENDER"));
                                }
			}else{
				exist= false;
			} 
                
          } catch (Exception e) { System.out.println(e);}  
          finally{  
                    if(rs!=null){  
                        rs.close();  
                    }if (ps!=null){  
                      ps.close();  
                  }if(con!=null){  
                      con.close();  
                  }   
                } 
                if (exist){
            return user;
        }
        else return null;
      } 
       
       public  ArrayList<User> getUsersByName(String name) throws SQLException  
      {  
        ArrayList<User> users = new ArrayList<>();
        User user=new User();
        boolean exist=false;
        Connection con = null;  
        PreparedStatement ps = null;  
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
           try {  
                      
                        con=this.getConnection();  
                        ps=con.prepareStatement("select * from USERINFO WHERE FULLNAME LIKE '%?%'");
                        ps.setString(1, name); 
                        
                        rs=ps.executeQuery();
                        while(rs.next()){
                                user.setEmail(rs.getString("EMAIL"));
                                user.setCountry(rs2.getString("COUNTRY"));
                                user.setFullname(rs2.getString("FULLNAME"));
                                user.setGender(rs2.getString("GENDER"));
                                
                                ps2=con.prepareStatement("Select * from USERLOGIN WHERE EMAIL =?");
                                ps2.setString(1, user.getEmail());
                                rs2=ps2.executeQuery();
                                if(rs2.next()){
                                    user.setMode(rs.getInt("USERMODE"));
                                    user.setPassword(rs.getString("PASSWORD"));
                                    user.setUsername(rs.getString("USERNAME"));
                                    user.setStatus(rs.getInt("USERSTATUS"));
                                }
                                users.add(user);
			}
                
          } catch (Exception e) { System.out.println(e);}  
          finally{  
                    if(rs!=null){  
                        rs.close();  
                    }if (ps!=null){  
                      ps.close();  
                  }if(con!=null){  
                      con.close();  
                  }   
                } 
        return users;
        
      }
      
     // to update the password for the given username  
      public int update(String name, String password) throws SQLException  {  
              Connection c=null;  
              PreparedStatement ps=null;  
                
              int recordCounter=0;  
              try {  
                      c=this.getConnection();  
                      ps=c.prepareStatement(" update userdata set upassword=? where uname='"+name+"' ");  
                      ps.setString(1, password);  
                      recordCounter=ps.executeUpdate();  
              } catch (Exception e) {  e.printStackTrace(); } finally{  
                      
                  if (ps!=null){  
                      ps.close();  
                  }if(c!=null){  
                      c.close();  
                  }   
               }  
             return recordCounter;  
          }  
            
// to delete the data from the database   
         public int delete(int userid) throws SQLException{  
              Connection c=null;  
              PreparedStatement ps=null;  
              int recordCounter=0;  
              try {  
                       c=this.getConnection();  
                      ps=c.prepareStatement(" delete from userdata where uid='"+userid+"' ");  
                      recordCounter=ps.executeUpdate();  
              } catch (Exception e) { e.printStackTrace(); }   
              finally{  
              if (ps!=null){  
                      ps.close();  
             }if(c!=null){  
                      c.close();  
              }   
           }  
             return recordCounter;  
          } 
         
         public ResultSet getUserFriends(String email) throws SQLException, ClassNotFoundException{
            Connection con = this.getConnection();
            PreparedStatement pst = con.prepareStatement("select friendemail from userfriends where email = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs;
         }
         public ResultSet getUserFriendsData(String friendEmail) throws ClassNotFoundException, SQLException{
            Connection con = this.getConnection();
            PreparedStatement query = con.prepareStatement("select a.email,a.fullname,a.gender,a.country,b.userstatus,b.usermode \n"
                        + "from userinfo a,userlogin b\n"
                        + "where \n"
                        + "a.email = b.email\n"
                        + "and a.email = ? ",
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            query.setString(1, friendEmail);
            ResultSet rs = query.executeQuery();
            return rs;
         }
}
