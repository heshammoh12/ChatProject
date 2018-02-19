/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import client.interfaces.SignUpValidationInter;
import client.models.ClientImpl;
import client.models.SignUpValidInterImp;
import iti.chat.common.LogInVerificationInter;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import iti.chat.common.SignUpVerificationInter;
import java.awt.Color;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Hesham Kadry
 */
public class FXMLSignUpPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //ObservableList<String>Country = FXCollections.observableArrayList("Egypt","Morocco","Tunis","Other");
    @FXML
    private ComboBox SignUpPage_CompoBox_Country;
    @FXML
    private AnchorPane anchorSignup;
    @FXML
    private TextField SignUpPage_TextField_FullName;
    @FXML
    private TextField SignUpPage_TextField_UserName;
    @FXML
    private TextField SignUpPage_TextField_Email;
    @FXML
    private TextField SignUpPage_TextField_Password;
    @FXML
    private RadioButton SignUpPage_RadioButton_Male;
    @FXML
    private RadioButton SignUpPage_RadioButton_Female;
    @FXML
    private Button SignUpPage_btn_SignUp;

    private SignUpValidInterImp signUpImpl;
    private Registry registry = null;
    private ServerInter server = null;
    private LogInVerificationInter logInVerificationInter = null;
    private SignUpVerificationInter signUpVerificationInter = null;
    private double xOffset = 0;
    private double yOffset = 0;
    private FXMLLoader loader;
    private Parent root;

    private boolean isValidPass = false;
    private boolean isValidFullName = false;
    private boolean isValidUserName = false;
    private boolean isValidMail = false;

    private Stage stage;
    @FXML
    private Button SignUP_BtnClose;
    @FXML
    private Button SignUp_BtnMin;
    @FXML
    private ToggleGroup GenderGroup;
    @FXML
    private Button backButton;

    public void setRegistry(Registry registry) {
        this.registry = registry;
        System.out.println("set registry");
    }

    public void setLogInVerificationInter(LogInVerificationInter logInVerificationInter) {
        this.logInVerificationInter = logInVerificationInter;
        System.out.println("set LogInVerificationInter");
    }

    public Registry getRegistry() {
        return registry;
    }

    public LogInVerificationInter getLogInVerificationInter() {
        return logInVerificationInter;
    }

    public void setSignUpVerificationInter(SignUpVerificationInter signUpVerificationInter) {
        this.signUpVerificationInter = signUpVerificationInter;
        System.out.println("set SignUpVerificationInter");
    }

    public SignUpVerificationInter getSignUpVerificationInter() {
        return signUpVerificationInter;
    }

    public ServerInter getServer() {
        return server;
    }

    public void setServer(ServerInter server) {
        this.server = server;
    }

    @FXML
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) anchorSignup.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) anchorSignup.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void back(ActionEvent event) {
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/fxml/Scene.fxml").openStream());
            FXMLController controller = (FXMLController) loader.getController();
            controller.setRegistry(getRegistry());
            controller.setSignUpVerificationInter(getSignUpVerificationInter());
            controller.setLogInVerificationInter(getLogInVerificationInter());
            stage = (Stage) anchorSignup.getScene().getWindow();
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

    public void emailValidation() {
        SignUpPage_TextField_Email.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                } else {
                    if (!signUpImpl.emailValid(SignUpPage_TextField_Email.getText())) {
                        SignUpPage_TextField_Email.setStyle("-fx-text-fill: red;");
                        isValidMail = false;
                    } else {
                        SignUpPage_TextField_Email.setStyle("-fx-text-fill: green;");
                        isValidMail = true;
                    }
                }
            }
        });
    }

    public void fullNameValidation() {
        SignUpPage_TextField_FullName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                } else {
                    if (!signUpImpl.fullNameValid(SignUpPage_TextField_FullName.getText())) {
                        SignUpPage_TextField_FullName.setStyle("-fx-text-fill: red;");
                        isValidFullName = false;
                    } else {
                        SignUpPage_TextField_FullName.setStyle("-fx-text-fill: green;");
                        isValidFullName = true;
                    }
                }
            }
        });
    }

    public void userNameValidation() {
        SignUpPage_TextField_UserName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                } else {
                    if (!signUpImpl.userNameValid(SignUpPage_TextField_UserName.getText())) {
                        SignUpPage_TextField_UserName.setStyle("-fx-text-fill: red;");
                        isValidUserName = false;
                    } else {
                        SignUpPage_TextField_UserName.setStyle("-fx-text-fill: green;");
                        isValidUserName = true;
                    }
                }
            }
        });
    }

    public void passwordValidation() {
        SignUpPage_TextField_Password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                } else {
                    if (!signUpImpl.passValid(SignUpPage_TextField_Password.getText())) {
                        SignUpPage_TextField_Password.setStyle("-fx-text-fill: red;");
                        isValidPass = false;
                    } else {
                        SignUpPage_TextField_Password.setStyle("-fx-text-fill: green;");
                        isValidPass = true;
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        signUpImpl = new SignUpValidInterImp();
        emailValidation();
        fullNameValidation();
        userNameValidation();
        passwordValidation();
        initializeCompoBox();
    }

    public void initializeCompoBox() {
        SignUpPage_CompoBox_Country.getItems().removeAll(SignUpPage_CompoBox_Country.getItems());
        SignUpPage_CompoBox_Country.getItems().addAll("Other", "Egypt", "Morocco", "Tunis");
        SignUpPage_CompoBox_Country.getSelectionModel().select("Other");
    }

    @FXML
    private void getSignUpData(ActionEvent event) {
        if (validData()) {
            if (isValidMail && isValidUserName && isValidFullName && isValidPass) {

                if (registry == null || server == null && logInVerificationInter == null) {
                    seviceLookUp();
                }
                try {
                    if(signUpVerificationInter!=null){
                    User user = buildUser();
                    boolean exist = signUpVerificationInter.emailExists(user.getEmail());
                    if (!exist) {
                        boolean inserted = signUpVerificationInter.insertUser(user);
                        if (inserted) {
                            startChat(user);
                        } else {
                            showAlert("Error in Insertion");
                        }
                    } else {
                        showAlert("UserName Already Exist !!!!");
                    }
                    }
                } catch (Exception ex) {
                    showAlert("Sorry currently the server is under maintenance");
                    Logger.getLogger(FXMLSignUpPageController.class.getName()).log(Level.FINEST, null, ex);

                }

            }
        
            else {
                showAlert("Please correct all your data !!!");
            }
        }
    }

    private void seviceLookUp() {
        try {
            setRegistry(LocateRegistry.getRegistry(2000));
            //setRegistry(LocateRegistry.getRegistry("10.118.49.2",2000));
            setServer((ServerInter) registry.lookup("ChatService"));
            setSignUpVerificationInter((SignUpVerificationInter) registry.lookup("SignUpVary"));

        } catch (NotBoundException | RemoteException ex) {
            showAlert("Sorry currently the server is under maintenance");
            Logger.getLogger(FXMLSignUpPageController.class.getName()).log(Level.OFF, null, ex);
        }
    }

    private User buildUser() {
        String fullName, userName, email, password, country, gender;
        gender = "";
        fullName = SignUpPage_TextField_FullName.getText();
        userName = SignUpPage_TextField_UserName.getText();
        email = SignUpPage_TextField_Email.getText();
        password = SignUpPage_TextField_Password.getText();
        country = SignUpPage_CompoBox_Country.getValue().toString();
        if (SignUpPage_RadioButton_Female.isSelected()) {
            gender = "f";
        } else if (SignUpPage_RadioButton_Male.isSelected()) {
            gender = "m";
        }

        User user = new User();
        user.setFullname(fullName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setGender(gender);
        user.setCountry(country);
        user.setPassword(password);
        user.setMode(1);
        user.setStatus(1);
        return user;
    }

    private boolean validData() {
        String fullName, userName, email, password, country, gender;
        gender = "";
        fullName = SignUpPage_TextField_FullName.getText();
        userName = SignUpPage_TextField_UserName.getText();
        email = SignUpPage_TextField_Email.getText();
        password = SignUpPage_TextField_Password.getText();
        country = SignUpPage_CompoBox_Country.getValue().toString();
        if (SignUpPage_RadioButton_Female.isSelected()) {
            gender = "f";
        } else if (SignUpPage_RadioButton_Male.isSelected()) {
            gender = "m";
        }
        System.out.println("fullName : " + fullName + " " + fullName.isEmpty() + " || userName : " + userName + " " + userName.isEmpty() + " || email : " + email.isEmpty() + " || password : " + password.isEmpty() + " || country : " + country.isEmpty());
        if (SignUpPage_CompoBox_Country.getValue().toString().equals("Other")) {
            showAlert("Please fill all data !!!");
            return false;
        } else {
            if (!fullName.isEmpty() && !userName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                System.out.println(" isValidMail " + isValidMail + " isValidUserName " + isValidUserName + " isValidFullName " + isValidFullName + "isValidPass" + isValidPass);
                return true;
            } else {
                showAlert("Please fill all data !!!");
                return false;
            }

        }

    }

    private void startChat(User user) {
        try {
            loader = new FXMLLoader();
            System.out.println("Loged in");
            root = loader.load(getClass().getResource("/fxml/ChatPage.fxml").openStream());
            ChatPageController chatController = (ChatPageController) loader.getController();
            chatController.setLoginer(user);
            chatController.buildChatPageList(user.getEmail());
            ClientImpl clientImpl = new ClientImpl(user);
            clientImpl.setChatPageController(chatController);
            chatController.setClient(clientImpl);
            server.registerClint(clientImpl);
            Stage stage = (Stage) anchorSignup.getScene().getWindow();
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

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }

}
