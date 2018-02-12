/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import iti.chat.common.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Hesham Kadry
 */
public class ChatPageController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @FXML
    private ListView ChatPage_List_OnlineUsers;
    @FXML
    private TabPane ChatPage_TabPane_Users;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ChatPage_TabPane_Users.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        ObservableList<User> onlineUsers = FXCollections.observableArrayList();
        onlineUsers.add(new User("h@yahoo.com","heshamKadry",1,"Male"));
        onlineUsers.add(new User("kadry@gmail.com","Kadry",1,"Male"));
        onlineUsers.add(new User("noora@yahoo.com","nora1990",1,"Female"));
        onlineUsers.add(new User("lol@yahoo.com","kareem",1,"Male"));
        ChatPage_List_OnlineUsers.setItems(onlineUsers);        
        ChatPage_List_OnlineUsers.setCellFactory(new Callback<ListView<User>, ListCell<User>>()
        {
            @Override
            public ListCell<User> call(ListView<User> listView)
            {
                return new ListFormat();
            }
        });  
        
    }

               
    public class ListFormat extends ListCell<User>
    {
        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            
            if(item!=null && !empty)
            {
             HBox pictureRegion = new HBox();
            Text text = new Text(item.getEmail());
            File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\CustomList\\src\\customlist\\personal-website-design.png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            pictureRegion.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                       System.out.println("presseeeeed");
                       System.out.println(item.getGender());
                       if(ChatPage_TabPane_Users.getTabs().size()<=5)
                       {
                           try {
                               Tab tab = new Tab(item.getUsername());
                               //Button tabA_button = new Button("Button@Tab A");
                               
                               Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/fxml/FXMLSignUpPage.fxml"));
                               //secPane.getChildren().add(newLoadedPane);
                               
                               tab.setContent(newLoadedPane);
                               ChatPage_TabPane_Users.getTabs().add(tab);
                           } catch (IOException ex) {
                               Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                       }else{
                           System.out.println("that is enough");
                       }
                       
                       
                }
            });
            
            pictureRegion.getChildren().add(imageView);
            pictureRegion.getChildren().add(text);
            setGraphic(pictureRegion);
            }else{
                setGraphic(null);
            }
            
        }       
    }    
    
}
