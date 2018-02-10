package client.controllers;

import iti.chat.common.ClientInter;
import iti.chat.common.ServerInter;
import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLController extends UnicastRemoteObject implements Initializable {
    
   
    /**
     * Initializes the controller class.
     */
    private double xOffset = 0;
    private double yOffset = 0;
    Stage stage ;
    private ServerInter server = null;

    public ServerInter getServer() {
        return server;
    }
    ClientInter client;
    Registry registry ;
    
    @FXML
    private AnchorPane Anchor;
    @FXML
    private TextField TextFieldUserName;
    @FXML
    private TextField TextFieldPassword;
    
    public FXMLController() throws RemoteException
    {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    //close Button Action
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.close();
    }
    //minimize Button Action
    @FXML
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.setIconified(true);
    }
    //SignUP to open signup Scene Button Action
    @FXML
    private void signUp(ActionEvent event) {
        FXMLLoader loader;
        Parent root; 
        try {
            loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/fxml/FXMLSignUpPage.fxml").openStream());
             stage = (Stage) Anchor.getScene().getWindow();
            // to make it draggable
            root.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                }    
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toString());
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
//            Logger.getLogger(FXMLFirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void logIn(ActionEvent event)
    {
        FXMLLoader loader;
        Parent root; 
        loader = new FXMLLoader();
        String email,password;
        email = TextFieldUserName.getText();
        password = TextFieldPassword.getText();
        
        
        try {
            registry = LocateRegistry.getRegistry(2000);
            server = (ServerInter) registry.lookup("ChatService");
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            root = loader.load(getClass().getResource("/fxml/ChatPage.fxml").openStream());
            Stage stage = (Stage) Anchor.getScene().getWindow();
            
            root.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                }    
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                }
            });
            System.out.println("before if");
            if(email.equals("hesham"))
            {
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toString());
            stage.setScene(scene);
            
            
                System.out.println("after if");
                stage.show();
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    

  
}
