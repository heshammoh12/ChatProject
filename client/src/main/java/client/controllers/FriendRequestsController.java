/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import iti.chat.common.User;
import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import iti.chat.common.ClientInter;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author Dina PC
 */
public class FriendRequestsController implements Initializable {

    @FXML
    private ListView ListView_AcceptFriend;

    private User loginer;
    private Registry registry = null;
    private ServerInter server = null;
    private ClientInter client = null;
    private ObservableList<User> friendRequests;
    private ArrayList<User> userfriendRequests;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendRequests = FXCollections.observableArrayList();
        ListView_AcceptFriend.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> listView) {
                return new FriendRequestsController.ListFormat();
            }
        });
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setServer(ServerInter server) {
        this.server = server;
    }

    public void setClient(ClientInter client) {
        this.client = client;
    }

    public Registry getRegistry() {
        return registry;
    }

    public ServerInter getServer() {
        return server;
    }

    public ClientInter getClient() {
        return client;
    }

    public void setLoginer(User loginer) {
        this.loginer = loginer;
        System.out.println("this + " + this);
    }

    public User getLoginer() {
        return loginer;
    }

    public class ListFormat extends ListCell<User> {

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            if (item != null && !empty) {
                HBox hboxButton = new HBox();
                HBox pictureRegion = new HBox();
                Text text = new Text(item.getEmail());
//                File file = new File("\\resources\\images\\personal.png");
                Button acceptBtn = new Button("Ok");
                Button rejectBtn = new Button("X");
                //accept the friend request
                acceptBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (server != null) {
                            try {
                                server.acceptFriendRequest(client.getUser().getEmail(), item.getEmail());
                                friendRequests.remove(item);
                                if(client !=null){
                                    client.appendNewFriend(item);
                                }
                            } catch (RemoteException ex) {
                                showAlert("Sorry currently the server is under maintenance");
                                Logger.getLogger(FriendRequestsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

                //reject the friend request
                rejectBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (server != null) {
                            try {
                                server.rejectFriendRequest(client.getUser().getEmail(), item.getEmail());
                                friendRequests.remove(item);
                            } catch (RemoteException ex) {
                                showAlert("Sorry currently the server is under maintenance");
                                Logger.getLogger(FriendRequestsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                });

                hboxButton.getChildren().add(acceptBtn);
                hboxButton.getChildren().add(rejectBtn);
                hboxButton.setAlignment(Pos.CENTER_RIGHT);
                pictureRegion.setHgrow(hboxButton, Priority.ALWAYS);
                Image image=null;
               try {
                    if(item.getGender().equalsIgnoreCase("f")){
                        image = new Image(this.getClass().getResource("/images/User2.png").toURI().toString());
                    }else{
                    
                        image = new Image(this.getClass().getResource("/images/User.png").toURI().toString());
                    }
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
               ImageView imageView = new ImageView(image);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                pictureRegion.getChildren().add(imageView);
                pictureRegion.getChildren().add(text);
                pictureRegion.getChildren().add(hboxButton);
                pictureRegion.setPadding(new Insets(2));
                setGraphic(pictureRegion);
            } else {
                setGraphic(null);
            }

        }
    }

    public void getRequests() {
        System.out.println("getRequests");
        try {
            System.out.println(client.getUser().getEmail());
        } catch (RemoteException ex) {
            Logger.getLogger(FriendRequestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (server != null) {
            try {
                System.out.println("server is not null");
                userfriendRequests = server.getfriendRequests(client.getUser().getEmail());
                for (User friendRequest : userfriendRequests) {

                    System.out.println(friendRequest.getEmail());
                }
                friendRequests = FXCollections.observableArrayList(userfriendRequests);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ListView_AcceptFriend.setItems(friendRequests);
                    }

                });
            } catch (RemoteException ex) {
                Logger.getLogger(FriendRequestsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }
    

    public void appendNewFriendRequest(User newFriend){
        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                         friendRequests.add(newFriend);
                    }

                });
    }


}
