/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import iti.chat.common.ClientInter;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ChatPageController implements Initializable {

    private FXMLLoader loader2;
    private FXMLLoader chatGroupLoader;
    private FXMLLoader requestsLoader;
    private Parent root2;
    @FXML
    private ListView ChatPage_List_OnlineUsers;
    @FXML
    private TabPane ChatPage_TabPane_Users;
    @FXML
    private Tab tabAllUsers;
    @FXML
    private Tab ChatPageGroupChattap;
    @FXML
    private Tab friendRequestTab;
    @FXML
    private ComboBox ChatBox_ComboBox_Mode;
    @FXML
    private Label text_user_name;

    @FXML
    private TextField annoncmentsField;

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
    private FriendRequestsController requestController;
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
        Platform.runLater(() -> {
            text_user_name.setText(loginer.getUsername());
        });
    }

    public User getLoginer() {
        return loginer;
    }

    public ClientInter getClient() {
        return client;
    }

    public void setClient(ClientInter client) {
        this.client = client;
        setComboBox_Mode();
    }

    public void setComboBox_Mode() {

        try {
            if (this.client != null && this.client.getUser() != null) {
                int mode = this.client.getUser().getMode();
                if (mode == 1) {
                    ChatBox_ComboBox_Mode.getSelectionModel().select("Online");
                } else if (mode == 2) {
                    ChatBox_ComboBox_Mode.getSelectionModel().select("Busy");
                } else {
                    ChatBox_ComboBox_Mode.getSelectionModel().select("Away");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            seviceLookUp();
            onlineUsers = FXCollections.observableArrayList();
            ChatPage_TabPane_Users.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            ImageView searchIcon = new ImageView(new Image(this.getClass().getResource("/images/search.png").toURI().toString())); // for example
            searchIcon.setFitWidth(20);
            searchIcon.setFitHeight(20);
            tabAllUsers.setGraphic(searchIcon);
            ChatPage_List_OnlineUsers.setItems(onlineUsers);
            ImageView friendRequestTabImage = new ImageView(new Image(this.getClass().getResource("/images/req.png").toURI().toString())); // for example
            friendRequestTabImage.setFitWidth(20);
            friendRequestTabImage.setFitHeight(20);
            friendRequestTab.setGraphic(friendRequestTabImage);
//            friendRequestTab.setOnSelectionChanged(new EventHandler<Event>() {
//                @Override
//                public void handle(Event event) {
//                    System.out.println("req is selected");
//                    System.out.println( ChatPage_TabPane_Users.getSelectionModel().getSelectedIndex());
//
//                }
//            });
            setFrindesListFactory();
            //hesham
            //addNewSearchPane();
            initializeModeCompoBox();

            //nagib
            openedTabs = new HashMap<>();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class ListFormat extends ListCell<User> {

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

            if (item != null && !empty) {
                HBox statuse = new HBox();
                HBox pictureRegion = new HBox();
                Text text = new Text(item.getFullname());
//                File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\CustomList\\src\\customlist\\personal-website-design.png");
                Image image = null;
                try {
                    if (item.getGender().equalsIgnoreCase("f")) {
                        image = new Image(this.getClass().getResource("/images/User2.png").toURI().toString());
                    } else {

                        image = new Image(this.getClass().getResource("/images/User.png").toURI().toString());
                    }
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ;
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                //circle for online and offline users
                cir = new Circle(10, 10, 5);
                if (item.getStatus() == 1) {
                    if (item.getMode() == 1) {
                        System.out.println("mode +++"+1);
                        cir.setFill(Color.LAWNGREEN);
                    } else if (item.getMode() == 2) {
                        System.out.println("mode +++"+3);
                        cir.setFill(Color.ORANGE);
                    } else {
                        System.out.println("mode +++"+2);
                        cir.setFill(Color.RED);
                    }
//                    getNotification(item.getFullname(),2);
                } else {
                    cir.setFill(Color.GREY);
                    //getNotification(item.getFullname(),3);
                }

                pictureRegion.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("presseeeeed");
                        System.out.println(item.getFullname());
                        if (noTwoTabs(item)) {

                            ClientInter frindClint = getFrindClint(item.getEmail());
                            if (frindClint != null) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Tab tab = new Tab(item.getFullname());
                                            String s = tabAllUsers.getStyle();
                                            //System.out.println("styleeeeeeeeeeeeee is  "+s);
                                            tab.getStyleClass().add("tabs");
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
                                showAlert("Sorry " + item.getFullname() + " is not online");
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
            setRegistry(LocateRegistry.getRegistry("10.118.49.2",2000));
            //setRegistry(LocateRegistry.getRegistry(2000));
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

    public void appendNewFriend(User newFriend) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onlineUsers.add(newFriend);
            }

        });
    }

    public void appendNewFriendRequest(User newFriend) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                requestController.appendNewFriendRequest(newFriend);
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
                            tab.getStyleClass().add("tabs");
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
                                tab.getStyleClass().add("tabs");
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

    private boolean noTwoTabs(User user) {
        for (Map.Entry<String, ChatBoxController> entry : openedTabs.entrySet()) {
            String t = entry.getKey();
            ChatBoxController u = entry.getValue();
            System.out.println("search in the list ");
            try {
                if (u.getRecievers().size() == 1 && u.getRecievers().get(0).getUser().getEmail().equalsIgnoreCase(user.getEmail())) {

                    return false;

                }
            } catch (RemoteException ex) {
                Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;

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

    public void friendChangeState(ClientInter newOnlineclient, int state) {
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

    public void friendChangeMode(ClientInter newOnlineclient, int mode) {
        Platform.runLater(() -> {
            // Where the magic happens.

            onlineUsers.forEach((t) -> {

                try {
                    if (t.getEmail().equals(newOnlineclient.getUser().getEmail())) {
                        System.out.println("dina is found in friendChangeMode");
                        System.out.println("mode is ---->" + mode);
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
    public void addFriendRequestPane(ClientInter clientInter, ServerInter serverInter, Registry r) {

        try {
            requestsLoader = new FXMLLoader();
            Pane newPane = requestsLoader.load(getClass().getResource("/fxml/friendRequests.fxml").openStream());
            requestController = (FriendRequestsController) requestsLoader.getController();
            requestController.setClient(clientInter);
            requestController.setRegistry(r);
            requestController.setServer(serverInter);
            requestController.setLoginer(clientInter.getUser());
            requestController.getRequests();
            friendRequestTab.setContent(newPane);
        } catch (IOException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //
    public void getAnnoncment(String Content) {
        System.out.println("client side annoncment is " + Content);
        annoncmentsField.setText(Content);
        this.getNotification(Content, 1);
    }

    public void getNotification(String content, int type) {

        Platform.runLater(() -> {
            TrayNotification tray = null;
            switch (type) {
                case 1:
                    tray = new TrayNotification("Notification", "Annoncment from server..Please Check Your Home", NotificationType.NOTICE);
                    break;
                case 2:
                    tray = new TrayNotification("Notification", "Your Friend " + content + " Is Online", NotificationType.NOTICE);
                    break;
                case 3:
                    tray = new TrayNotification("Notification", "Your Friend " + content + " Is Offline", NotificationType.NOTICE);
                    break;
                case 4:
                    tray = new TrayNotification("Friend Request ", content + " Is Now Your Friend", NotificationType.NOTICE);
                    break;
                case 5:
                    tray = new TrayNotification("Friend Request ", content + " Wants To Add You As A Friend", NotificationType.NOTICE);
                    break;
                default:
                    tray = new TrayNotification("Notification", "Please Be notified", NotificationType.NOTICE);
            }
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndWait();
        });
    }

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

    /*    
    public void addGroupChatPane(ClientInter clientInter)
    {
        try {
            chatGroupLoader = new FXMLLoader();
            
            Pane newLoadedPane = chatGroupLoader.load(getClass().getResource("/fxml/ChatGroup.fxml").openStream());
            ChatGroupController chatGroupController = (ChatGroupController) chatGroupLoader.getController();
            //chatGroupController.setLoginer(clientInter.getUser());
            ChatPageGroupChattap.setContent(newLoadedPane);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
     */
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
            if (ChatBox_ComboBox_Mode.getValue().toString().equals("Online")) {
                checkRowAffected = server.updateMode(client, 1, getLoginer().getEmail());
                System.out.println("afftected rows " + checkRowAffected);
            } else if (ChatBox_ComboBox_Mode.getValue().toString().equals("Busy")) {
                checkRowAffected = server.updateMode(client, 2, getLoginer().getEmail());
            } else {
                checkRowAffected = server.updateMode(client, 3, getLoginer().getEmail());
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
