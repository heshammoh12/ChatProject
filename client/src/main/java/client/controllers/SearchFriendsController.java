package client.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import iti.chat.common.ClientInter;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
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


public class SearchFriendsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ArrayList<User> allUser;
    private ServerInter server = null;
    private ClientInter client = null;
    String searchesEmail;
    private ObservableList<User> usersSearch;
    private ObservableList<User> dummy;
    @FXML
    private TextField TextField_SearchFriend;  
    @FXML
    private Button Button_SearchFriend;
    @FXML
    private ListView ListView_SearchFriend;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        //this commented area for just testing purpose
        /*
        ObservableList<Employee> ob = FXCollections.observableArrayList();
        ob.add(new Employee("hesha", "mohamed","152"));
        ob.add(new Employee("kadry", "mohamed","158"));
        ob.add(new Employee("zein", "mohamed","178"));
        ob.add(new Employee("koly", "mohamed","200"));
        */
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2000);
            server = (ServerInter) registry.lookup("ChatService");
        } catch (RemoteException ex) {
            Logger.getLogger(SearchFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SearchFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
              
        Button_SearchFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("button pressed");
                System.out.println(getUserData());
                usersSearch = FXCollections.observableArrayList(getUserData());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ListView_SearchFriend.setItems(usersSearch);
                    }

                });
            }
        });
        //customize list
        ListView_SearchFriend.setCellFactory(new Callback<ListView<User>, ListCell<User>>()
        {
            @Override
            public ListCell<User> call(ListView<User> listView)
            {
                return new ListFormat();
            }
        }); 
    }    
    
    
    public ArrayList<User> getUserData()
    {
        searchesEmail = TextField_SearchFriend.getText();
        allUser = new ArrayList<User>();
        try {
            allUser=server.search(searchesEmail);
            Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                server.updateStatistics();
                                server.registerClint(client);
                            } catch (RemoteException ex) {
                                Logger.getLogger(SearchFriendsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                    });
            
        } catch (RemoteException ex) {
            Logger.getLogger(SearchFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUser;
    }
    
    
        public class ListFormat extends ListCell<User> {

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

            if (item != null && !empty) {
                HBox pictureRegion = new HBox();
                Text text = new Text(item.getEmail());
                File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\CustomList\\src\\customlist\\personal-website-design.png");
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                pictureRegion.getChildren().add(imageView);
                pictureRegion.getChildren().add(text);
                setGraphic(pictureRegion);
            } else {
                setGraphic(null);
            }

        }
    }
        
    
}
