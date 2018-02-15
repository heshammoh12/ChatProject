/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import client.models.ClientImpl;
import iti.chat.common.ClientInter;
import iti.chat.common.LogInVerificationInter;
import iti.chat.common.ServerInter;
import iti.chat.common.SignUpVerificationInter;
import iti.chat.common.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChatPageController implements Initializable {

    @FXML
    private ListView ChatPage_List_OnlineUsers;
    @FXML
    private TabPane ChatPage_TabPane_Users;
    @FXML
    private Tab tabAllUsers;
    
    private ObservableList<User> onlineUsers;
    private ServerInter server = null;
    private Registry registry = null;
    private User loginer = null;
    private ClientInter client = null;

    public ServerInter getServer() {
        return server;
    }

    public void setServer(ServerInter server) {
        this.server = server;
    }

    public void setLoginer(User loginer) {
        this.loginer = loginer;
    }

    public User getLoginer() {
        return loginer;
    }

    public ClientInter getClient() {
        return client;
    }

    public void setClient(ClientInter client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        seviceLookUp();
        onlineUsers = FXCollections.observableArrayList();
        ChatPage_TabPane_Users.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        ChatPage_List_OnlineUsers.setItems(onlineUsers);
        setFrindesListFactory();
        //hesham
        addNewSearchPane();
        //
        
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
                pictureRegion.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("presseeeeed");
                        System.out.println(item.getGender());
                        if (ChatPage_TabPane_Users.getTabs().size() <= 5) {
                            try {
                                getFrindClint(item.getEmail());
                                Tab tab = new Tab(item.getFullname());
                                //Button tabA_button = new Button("Button@Tab A");
                                Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml"));
                                //secPane.getChildren().add(newLoadedPane);
                                tab.setContent(newLoadedPane);
                                ChatPage_TabPane_Users.getTabs().add(tab);
                            } catch (IOException ex) {
                                Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            System.out.println("that is enough");
                        }

                    }
                });
                pictureRegion.getChildren().add(imageView);
                pictureRegion.getChildren().add(text);
                setGraphic(pictureRegion);
            } else {
                setGraphic(null);
            }

        }
    }

    /*Methods added by Nagib  */
    @FXML
    private void close(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    private void seviceLookUp() {
        try {
            setRegistry(LocateRegistry.getRegistry(2000));
            setServer((ServerInter) registry.lookup("ChatService"));
        } catch (NotBoundException | RemoteException ex) {
            Logger.getLogger(FXMLSignUpPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildChatPageList(String mail) {
        try {
            server.getFrinds(mail).forEach((t) -> {
                onlineUsers.add(t);
//                System.out.println("friend is : " + t.getEmail());
            });
//            ArrayList<User> test = server.getFrinds("ahmed");

        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setFrindesListFactory() {
        ChatPage_List_OnlineUsers.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> listView) {
                return new ListFormat();
            }
        });

    }

    //
    /*Methods added by Nagib  */
    private void getFrindClint(String mail) {
        try {
            ClientInter friend = (ClientInter) server.getFriendClient(mail);
            if (friend != null) {
                System.out.println("friend is -> " + friend.getUser().getFullname());

            } else {
                System.out.println("friend is null-> " + friend);
            }

        } catch (RemoteException ex) {
            System.out.println("Error in getFrindClint method");
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //
    //
    /*Methods added by Dina  */
    //
    //
    /*Methods added by Hassna  */
    //
    //
    /*Methods added by Hesham  */
    //
    public void addNewSearchPane()
    {
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/fxml/searchFriends.fxml"));
            tabAllUsers.setContent(newLoadedPane);
        } catch (IOException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //
    /*Methods added by Fatma  */
    //
    //
}
