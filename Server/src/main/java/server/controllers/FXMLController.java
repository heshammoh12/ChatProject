package server.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import iti.chat.common.User;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.models.LogInVerificationImpl;
import server.models.SignUpVerificationImpl;
import server.models.ServerImpl;

public class FXMLController implements Initializable {

    @FXML
    private Label label;

    Registry registry = null;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

        try {
            if (registry == null) {
                registry = LocateRegistry.createRegistry(2000);
                registry.rebind("ChatService", new ServerImpl());
                registry.rebind("LogInVary", new LogInVerificationImpl());
                registry.rebind("SignUpVary", new SignUpVerificationImpl());
            }

        } catch (RemoteException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        label.setStyle("-fx-text-fill: green;");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
