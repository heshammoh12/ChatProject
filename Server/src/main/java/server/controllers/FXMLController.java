package server.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import iti.chat.common.User;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import server.models.LogInVerificationImpl;
import server.models.SignUpVerificationImpl;
import server.models.ServerImpl;

public class FXMLController implements Initializable {

    @FXML private Label label;
    @FXML private Button stopButton;
    @FXML private Button button;
    @FXML private PieChart genderStatistic;


    Registry registry=null;

    @FXML
    /*start server button*/
    private void handleButtonAction(ActionEvent event) {
        System.out.println("run server!");
        try {

            if(registry == null)
            {
                registry = LocateRegistry.createRegistry(2000);
            }
                registry.rebind("ChatService", new ServerImpl());
                registry.rebind("LogInVary", new LogInVerificationImpl());
                registry.rebind("SignUpVary", new SignUpVerificationImpl());
                label.setText("Server is Running");
                stopButton.setDisable(false);
                button.setDisable(true);
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*Stop server button*/
    @FXML
    private void stopServer(ActionEvent event){
        System.out.println("stop server!");
        try {
            registry.unbind("ChatService");
            registry.unbind("LogInVary");
            registry.unbind("SignUpVary");
            label.setText("Server is Stopped");
            button.setDisable(false);
            stopButton.setDisable(true);
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    /*Button action to show some statistcs*/
    public void showStatistics(ActionEvent event){
        ObservableList<PieChart.Data> details =  FXCollections.observableArrayList();
        details.addAll(new PieChart.Data("Male percentage", 60) , new PieChart.Data("Female percentage", 40));
        genderStatistic.setData(details);
        genderStatistic.setLabelsVisible(true);
        genderStatistic.setLegendSide(Side.TOP);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
