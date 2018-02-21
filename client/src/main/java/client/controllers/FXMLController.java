package client.controllers;

import client.models.ClientImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import iti.chat.common.ClientInter;
import iti.chat.common.LogInVerificationInter;
import iti.chat.common.ServerInter;
import iti.chat.common.SignUpVerificationInter;
import iti.chat.common.User;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLController extends UnicastRemoteObject implements Initializable {

    private FXMLLoader loader;
    private FXMLLoader loader2;
    private FXMLLoader loader3;
    private Parent root;
    private Parent root2;
    private Parent root3;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private Registry registry = null;
    private LogInVerificationInter logInVerificationInter = null;
    private SignUpVerificationInter signUpVerificationInter = null;
    private ServerInter server = null;
    private User user;
    private ClientInter client;

    @FXML
    private AnchorPane Anchor;
    @FXML
    private  JFXTextField TextFieldUserName;
    @FXML
    private  JFXPasswordField TextFieldPassword;
    @FXML
    private JFXButton WelcomPage_Button_Login;
    @FXML
    private JFXButton WelcomePage_Button_Close;
    @FXML
    private JFXButton WelcomePage_Button_minimize;
    @FXML
    private JFXButton WelcomPage_Button_SignUp;

    public FXMLController() throws RemoteException {
    }

    public ServerInter getServer() {
        return server;
    }

    public void setServer(ServerInter server) {
        this.server = server;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setLogInVerificationInter(LogInVerificationInter logInVerificationInter) {
        this.logInVerificationInter = logInVerificationInter;
    }

    public Registry getRegistry() {
        return registry;
    }

    public LogInVerificationInter getLogInVerificationInter() {
        return logInVerificationInter;
    }

    public void setSignUpVerificationInter(SignUpVerificationInter signUpVerificationInter) {
        this.signUpVerificationInter = signUpVerificationInter;
    }

    public SignUpVerificationInter getSignUpVerificationInter() {
        return signUpVerificationInter;
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
        Platform.exit();
        System.exit(0);
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
        try {
            loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/fxml/FXMLSignUpPage.fxml").openStream());
            FXMLSignUpPageController controller = (FXMLSignUpPageController) loader.getController();
            controller.setRegistry(getRegistry());
            controller.setSignUpVerificationInter(getSignUpVerificationInter());
            controller.setLogInVerificationInter(getLogInVerificationInter());
            stage = (Stage) Anchor.getScene().getWindow();
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
//            Logger.getLogger(FXMLFirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //logIn to open Chat Scene 
    @FXML
    private void logIn(ActionEvent event) {
        String email = TextFieldUserName.getText();
        String password = TextFieldPassword.getText();
        try {
            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Complete Data Please !!!!");
            } else {
                if (registry == null || server == null || logInVerificationInter == null) {
                    seviceLookUp();
                }
                if (logInVerificationInter != null) {
                    user = logInVerificationInter.login(email, password); 
                
                    if (user != null) {
                        startChat(user);
                    } else {
                        showAlert("Incorrect Username or Password !!!!");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void seviceLookUp() {
        try {
            setRegistry(LocateRegistry.getRegistry(2000));
            //setRegistry(LocateRegistry.getRegistry("10.118.49.2",2000));
            setServer((ServerInter) registry.lookup("ChatService"));
            setLogInVerificationInter((LogInVerificationInter) registry.lookup("LogInVary"));

        } catch (NotBoundException | RemoteException ex) {
            showAlert("Sorry currently the server is under maintenance");
            Logger.getLogger(FXMLSignUpPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startChat(User user) {
        try {
            loader = new FXMLLoader();
            loader2 = new FXMLLoader();
//            loader3 = new FXMLLoader();
            System.out.println("Loged in");
            root = loader.load(getClass().getResource("/fxml/ChatPage.fxml").openStream());
            root2 = loader2.load(getClass().getResource("/fxml/searchFriends.fxml").openStream());            
//            root3 = loader3.load(getClass().getResource("/fxml/f.fxml").openStream());
            
            
            
            ChatPageController chatController = (ChatPageController) loader.getController();
            chatController.setLoginer(user);
            chatController.buildChatPageList(user.getEmail());
            ClientImpl clientImpl = new ClientImpl(user);
            clientImpl.setChatPageController(chatController);
            chatController.setClient(clientImpl);
            chatController.addNewSearchPane(clientImpl);
            //chatController.addGroupChatPane(clientImpl);
            chatController.addFriendRequestPane(clientImpl,server,registry);
            server.registerClint(clientImpl);
            Stage stage = (Stage) Anchor.getScene().getWindow();
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
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
