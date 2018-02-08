/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iti.chat.projectserver;

import iti.chat.common.LoginInterface;
import iti.chat.common.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author Hasnaa Mohammed
 */
public class LoginImp implements LoginInterface {

    Connection con;
    Statement stmt;
    ResultSet rs;

    public LoginImp() {
        try {
            DriverManager.registerDriver(new OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "chat", "chat");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User login(String email, String password) {
        User user = new User();
        boolean exist = false;
        String queryString = "select * from USERLOGIN WHERE EMAIL ='" + email + "' AND PASSWORD ='" + password + "'";
        try {
            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                exist = true;

                user.setEmail(rs.getString("EMAIL"));
                user.setMode(rs.getInt("USERMODE"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setUsername(rs.getString("USERNAME"));
                user.setStatus(rs.getInt("USERSTATUS"));

                String queryString2 = "Select * from USERINFO WHERE EMAIL ='" + email + "'";
                ResultSet rs2 = stmt.executeQuery(queryString2);
                if (rs2.next()) {
                    user.setCountry(rs2.getString("COUNTRY"));
                    user.setFullname(rs2.getString("FULLNAME"));
                    user.setGender(rs2.getString("GENDER"));
                }

            } else {
                exist = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (exist) {
            return user;
        } else {
            return null;
        }
    }

}
