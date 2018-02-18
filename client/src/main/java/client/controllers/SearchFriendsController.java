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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.w3c.dom.events.Event;

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
    private User loginer ;
    String searchesEmail;
    private ObservableList<User> usersSearch;
    private ObservableList<User> dummy;
    @FXML
    private TextField TextField_SearchFriend;  
    @FXML
    private Button Button_SearchFriend;
    @FXML
    private ListView ListView_SearchFriend;
    
    public void setLoginer(User loginer) {
        this.loginer = loginer;
        System.out.println("this + "+this);
    }

    public User getLoginer() {
        return loginer;
    }
    
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
            registry = LocateRegistry.getRegistry("10.118.49.2",2000);
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
        if(!searchesEmail.isEmpty())
        {
            try {
                if(!searchesEmail.equals(this.loginer.getEmail()))
                {   
                    allUser = server.search(searchesEmail);
                    for(User myFriend : server.getFrinds(this.loginer.getEmail()))
                    {
                        if(myFriend.getEmail().equals(searchesEmail))
                        {
                            allUser.clear();
                        }
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(SearchFriendsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return allUser;
    }
    
    
        public class ListFormat extends ListCell<User> {

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

            if (item != null && !empty) {
                HBox hboxButton = new HBox();
                HBox pictureRegion = new HBox();
                Text text = new Text(item.getEmail());
                File file = new File("\\resources\\images\\personal.png");
                Button btnAdd = new Button("add");
                
                btnAdd.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event) {
                        int rowAffected;
                            System.out.println(item.getEmail());
                            System.out.println("loginer is "+loginer.getEmail());
                        try {
                            rowAffected = server.addFriend(loginer.getEmail(), item.getEmail());
                            System.out.println("rowAffected "+rowAffected);
                            if(rowAffected>=1)
                            {
                                btnAdd.setDisable(true);
                            }
                        }catch (RemoteException ex) {
                            Logger.getLogger(SearchFriendsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                           
                    }
                });
                
                hboxButton.getChildren().add(btnAdd);
                hboxButton.setAlignment(Pos.CENTER_RIGHT);
                pictureRegion.setHgrow(hboxButton, Priority.ALWAYS);
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                pictureRegion.getChildren().add(imageView);
                pictureRegion.getChildren().add(text);
                pictureRegion.getChildren().add(hboxButton);
                pictureRegion.setPadding(new Insets(2));
                setGraphic(pictureRegion);
            }else {
                setGraphic(null);
            }

        }
    }
        
    
}
