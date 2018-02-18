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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChatPageController implements Initializable {

    private FXMLLoader loader2;
    private Parent root2;
    @FXML
    private ListView ChatPage_List_OnlineUsers;
    @FXML
    private TabPane ChatPage_TabPane_Users;
    @FXML
    private Tab tabAllUsers;
    @FXML
    private ComboBox ChatBox_ComboBox_Mode;

    Circle cir;
    private ObservableList<User> onlineUsers;
    private HashMap<String, ChatBoxController> openedTabs;
    private ServerInter server = null;
    private Registry registry = null;
    private User loginer = null;
    private ClientInter client = null;
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button exitButton;
    @FXML
    private Button logOutButton;
    @FXML
    private ListView<?> ChatPage_List_AllUsers;

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
        //addNewSearchPane();
        initializeModeCompoBox();

        //nagib
        openedTabs = new HashMap<>();
    }

    public class ListFormat extends ListCell<User> {

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

            if (item != null && !empty) {
                HBox statuse = new HBox();
                HBox pictureRegion = new HBox();
                Text text = new Text(item.getEmail());
                File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\CustomList\\src\\customlist\\personal-website-design.png");
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                //circle for online and offline users
                cir = new Circle(10, 10, 5);
                if (item.getStatus() == 1) {
                    if(item.getMode() == 1){
                    cir.setFill(Color.LAWNGREEN);
                    }
                    else if(item.getMode() == 2){
                    cir.setFill(Color.ORANGE);
                    }
                    else{
                    cir.setFill(Color.RED);
                    }
                } else {
                    cir.setFill(Color.GREY);
                }

                pictureRegion.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("presseeeeed");
                        System.out.println(item.getFullname());
                        if (ChatPage_TabPane_Users.getTabs().size() <= 50) {

                            ClientInter frindClint = getFrindClint(item.getEmail());
                            if (frindClint != null) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Tab tab = new Tab(item.getFullname());
                                            //Button tabA_button = new Button("Button@Tab A");
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatBox.fxml"));
                                            Pane root = (Pane) loader.load();
                                            ChatBoxController controller = (ChatBoxController) loader.getController();
                                            String openedTabId = LocalDateTime.now().toString() + controller.hashCode();
                                            controller.setMainClient(client);
                                            controller.setUsedTab(openedTabId);
                                            controller.setReciever(frindClint);
                                            controller.setServer(server);
                                            openedTabs.put(openedTabId, controller);
                                            //secPane.getChildren().add(newLoadedPane);
                                            tab.setOnClosed((event) -> {
                                                System.out.println("tab is closed");
                                                openedTabs.remove(openedTabId);
                                            });
                                            tab.setContent(root);
                                            ChatPage_TabPane_Users.getTabs().add(tab);
                                        } catch (IOException ex) {
                                            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            } else {
                                showAlert("Sorry your friend is not online");
                            }

                        } else {
                            System.out.println("that is enough");
                        }

                    }
                });
                statuse.getChildren().add(cir);
                statuse.setAlignment(Pos.CENTER_RIGHT);
                pictureRegion.setHgrow(statuse, Priority.ALWAYS);
                pictureRegion.getChildren().add(imageView);
                pictureRegion.getChildren().add(text);
                pictureRegion.getChildren().add(statuse);
                pictureRegion.setPadding(new Insets(2));
                setGraphic(pictureRegion);
            } else {
                setGraphic(null);
            }

        }
    }

    /*Methods added by Nagib  */
    @FXML
    private void close(ActionEvent event) {
        try {
            boolean isSompleted = server.signOurServer(client.getUser().getEmail());
            if (isSompleted) {
                server.unregisterClint(client);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            showAlert("Sorry the server currently is under maintenance");
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
            showAlert("Sorry the server currently is under maintenance");
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
    private ClientInter getFrindClint(String mail) {
        ClientInter friend = null;
        try {
            friend = (ClientInter) server.getFriendClient(mail);
            if (friend != null) {
                System.out.println("friend is -> " + friend.getUser().getFullname());
//                server.sendMessage(client, friend);

            } else {
                System.out.println("friend is null-> " + friend);
            }

        } catch (RemoteException ex) {
            System.out.println("Error in getFrindClint method");
            showAlert("Sorry the server currently is under maintenance");
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return friend;

    }

    public void recievefile(ClientInter sender, String tabId) {

//        System.out.println("recievefile ChatPageController ");
//        System.out.println("the file tab id is ->" + tabId);
        if (openedTabs == null) {
            openedTabs = new HashMap<>();
            System.out.println("openedTabs is null");
        }

        if (openedTabs.containsKey(tabId)) {
            System.out.println("found tabid in the list");
            openedTabs.get(tabId).requestRecieveFile(sender);
        } else {
            if (!tabIsOpened(sender, tabId)) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("open new tab");
                            Tab tab = new Tab(sender.getUser().getFullname());
                            //Button tabA_button = new Button("Button@Tab A");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatBox.fxml"));
                            Pane root = (Pane) loader.load();
                            ChatBoxController controller = (ChatBoxController) loader.getController();
                            System.out.println("controller obj is " + controller);
                            controller.setMainClient(client);
                            controller.setUsedTab(tabId);
                            controller.setReciever(sender);
                            controller.setServer(server);
                            openedTabs.put(tabId, controller);
                            //secPane.getChildren().add(newLoadedPane);
                            System.out.println("tab is + " + tab);
                            System.out.println("pan is + " + tab);
                            tab.setContent(root);
                            ChatPage_TabPane_Users.getTabs().add(tab);
                            controller.requestRecieveFile(sender);
                            System.out.println("tab is added");
                        } catch (IOException ex) {
                            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            }
        }

    }

    public void startSendingFile(String tabId) {
        if (openedTabs.containsKey(tabId)) {
            System.out.println("ChatPageController startSendingFile()");
            openedTabs.get(tabId).startSendingFile();
        }
    }

    public void recieveMessage(ClientInter sender) {

        System.out.println("recieveMessage ChatPageController ");
        try {
            System.out.println("the tab id is ->" + sender.getUser().getMessage().getTabId());
        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (openedTabs == null) {
            openedTabs = new HashMap<>();
            System.out.println("openedTabs is null");
        }
        try {
            if (openedTabs.containsKey(sender.getUser().getMessage().getTabId())) {
                openedTabs.get(sender.getUser().getMessage().getTabId()).recieveMessage(sender.getUser());
                System.out.println("found tabid in the list");
            } else {
                if (!tabIsOpened(sender)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("open new tab");
                                Tab tab = new Tab(sender.getUser().getFullname());
                                //Button tabA_button = new Button("Button@Tab A");
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatBox.fxml"));
                                Pane root = (Pane) loader.load();
                                ChatBoxController controller = (ChatBoxController) loader.getController();
                                System.out.println("controller obj is " + controller);
                                String openedTabId = sender.getUser().getMessage().getTabId();
                                controller.setMainClient(client);
                                controller.setUsedTab(openedTabId);
                                controller.setReciever(sender);
                                controller.setServer(server);
                                openedTabs.put(openedTabId, controller);
                                //secPane.getChildren().add(newLoadedPane);
                                System.out.println("tab is + " + tab);
                                System.out.println("pan is + " + tab);
                                tab.setContent(root);
                                ChatPage_TabPane_Users.getTabs().add(tab);
                                controller.recieveMessage(sender.getUser());
                                System.out.println("tab is added");
                            } catch (IOException ex) {
                                Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }

    private boolean tabIsOpened(ClientInter sender) {
        for (Map.Entry<String, ChatBoxController> entry : openedTabs.entrySet()) {
            String t = entry.getKey();
            ChatBoxController u = entry.getValue();
            System.out.println("search in the list ");
            if (u.getRecievers().size() == 1 && u.getRecievers().get(0).hashCode() == sender.hashCode()) {
                try {
                    u.setUsedTab(sender.getUser().getMessage().getTabId());
                    openedTabs.put(sender.getUser().getMessage().getTabId(), u);
                    u.recieveMessage(sender.getUser());
                    openedTabs.remove(t);
                    System.out.println("found tab with the same user");
                    return true;
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("u.getRecievers().size() " + u.getRecievers().size());
                System.out.println("u.getRecievers().get(0) " + u.getRecievers().get(0));
                System.out.println("Clint " + client);
            }
        }
        return false;

    }

    private boolean tabIsOpened(ClientInter sender, String tabId) {
        for (Map.Entry<String, ChatBoxController> entry : openedTabs.entrySet()) {
            String t = entry.getKey();
            ChatBoxController u = entry.getValue();
            System.out.println("search in the list ");
            if (u.getRecievers().size() == 1 && u.getRecievers().get(0).hashCode() == sender.hashCode()) {
                try {
                    u.setUsedTab(tabId);
                    openedTabs.put(tabId, u);
                    u.requestRecieveFile(sender);
                    openedTabs.remove(t);
                    System.out.println("found tab with the same user");
                    return true;
                } catch (Exception ex) {
                    Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("u.getRecievers().size() " + u.getRecievers().size());
                System.out.println("u.getRecievers().get(0) " + u.getRecievers().get(0));
                System.out.println("Clint " + client);
            }
        }
        return false;

    }

    @FXML
    private void logOut(ActionEvent event) {
        try {

            server.unregisterClint(client);

        } catch (RemoteException ex) {
            showAlert("Sorry currently the server is under maintenance");
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/fxml/Scene.fxml").openStream());
            FXMLController controller = (FXMLController) loader.getController();
            controller.setRegistry(registry);
            stage = (Stage) logOutButton.getScene().getWindow();
            // to make it draggable
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
            Logger.getLogger(FXMLSignUpPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static <T> void triggerUpdate(ListView<T> listView, T newValue, int i) {
        System.out.println("triggerUpdate");

        EventType<? extends ListView.EditEvent<T>> type = ListView.editCommitEvent();
        Event event = new ListView.EditEvent<>(listView, type, newValue, i);
        listView.fireEvent(event);
    }

    public void friendChangeState(ClientInter newOnlineclient , int state) {
        Platform.runLater(() -> {
            // Where the magic happens.
            
            onlineUsers.forEach((t) -> {
               
                try {
                    if (t.getEmail().equals(newOnlineclient.getUser().getEmail())) {
                        System.out.println("dina is found");
                        t.setStatus(state);
                        System.out.println("friendChangeState ChatPage");
                        triggerUpdate(ChatPage_List_OnlineUsers, t, onlineUsers.indexOf(t));
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        });

    }
    public void friendChangeMode(ClientInter newOnlineclient , int mode) {
        Platform.runLater(() -> {
            // Where the magic happens.
            
            onlineUsers.forEach((t) -> {
               
                try {
                    if (t.getEmail().equals(newOnlineclient.getUser().getEmail())) {
                        System.out.println("dina is found in friendChangeMode");
                        System.out.println("mode is ---->"+mode );
                        t.setMode(mode);
                        System.out.println("friendChangeMode ChatPage");
                        triggerUpdate(ChatPage_List_OnlineUsers, t, onlineUsers.indexOf(t));
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        });

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
    public void addNewSearchPane(ClientInter clientInter) {
        try {

            loader2 = new FXMLLoader();

            Pane newLoadedPane = loader2.load(getClass().getResource("/fxml/searchFriends.fxml").openStream());
            SearchFriendsController searchController = (SearchFriendsController) loader2.getController();
            searchController.setLoginer(clientInter.getUser());

            tabAllUsers.setContent(newLoadedPane);
        } catch (IOException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initializeModeCompoBox() {
        ChatBox_ComboBox_Mode.getItems().removeAll(ChatBox_ComboBox_Mode.getItems());
        ChatBox_ComboBox_Mode.getItems().addAll("Online", "Busy", "Away");
        ChatBox_ComboBox_Mode.getSelectionModel().select("Online");
    }

    @FXML
    private void comboAction(ActionEvent event) {
        System.out.println(ChatBox_ComboBox_Mode.getValue());
        System.out.println("mail is " + getLoginer().getEmail());
        int checkRowAffected;
        try {
            if(ChatBox_ComboBox_Mode.getValue().toString().equals("Online")){
                checkRowAffected = server.updateMode(client,1, getLoginer().getEmail());
            System.out.println("afftected rows " + checkRowAffected);
            }
            else if(ChatBox_ComboBox_Mode.getValue().toString().equals("Busy")){
            checkRowAffected = server.updateMode(client,2, getLoginer().getEmail());
            }
            else{
            checkRowAffected = server.updateMode(client,3, getLoginer().getEmail());
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //
    /*Methods added by Fatma  */
    //
    //
}
