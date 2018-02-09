/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iti.chat.clint;

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

/**
 * FXML Controller class
 *
 * @author Hesham Kadry
 */
public class FXMLSignUpPageController implements Initializable, SignUpValidationInter {

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

    @FXML
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) anchorSignup.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) anchorSignup.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initializeCompoBox();
    }

    public void initializeCompoBox() {
        SignUpPage_CompoBox_Country.getItems().removeAll(SignUpPage_CompoBox_Country.getItems());
        SignUpPage_CompoBox_Country.getItems().addAll("Other", "Egypt", "Morocco", "Tunis");
        SignUpPage_CompoBox_Country.getSelectionModel().select("Other");
    }

    @Override
    public boolean emailValid(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean userNameValid(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean fullNameValid(String fullName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passValid(String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User login(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void getSignUpData(ActionEvent event) {
        String fullName, userName, email, password, country, gender;
        gender = "";
        fullName = SignUpPage_TextField_FullName.getText();
        userName = SignUpPage_TextField_UserName.getText();
        email = SignUpPage_TextField_Email.getText();
        password = SignUpPage_TextField_Password.getText();
        country = SignUpPage_CompoBox_Country.getValue().toString();
        if (SignUpPage_RadioButton_Female.isSelected()) {
            gender = "Female";
        } else if (SignUpPage_RadioButton_Male.isSelected()) {
            gender = "male";
        }
        System.out.println(fullName + "/ " + userName + "/ " + email + "/ " + password + "/ " + "/ " + country + "/ " + gender);
        if (fullName.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty() || country.isEmpty()) {

        }
    }
}
