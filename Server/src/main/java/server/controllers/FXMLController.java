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
import java.util.Map;
import javafx.util.Callback;
import javafx.util.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import server.models.DBconnect;
import server.models.LogInVerificationImpl;
import server.models.SignUpVerificationImpl;
import server.models.ServerImpl;

public class FXMLController implements Initializable {

    @FXML private Label label;
    @FXML private Button stopButton;
    @FXML private Button button;
    @FXML private PieChart genderStatistic;
    @FXML private BarChart countriesStatistic;
    @FXML private CategoryAxis yAxis;
    @FXML private NumberAxis xAxis;
    @FXML private ListView onlineList; 
    @FXML private ListView offlineList; 
    @FXML private TextArea annText;
    @FXML private Button close;

    Registry registry=null;
    ServerImpl serverInstant =null;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label.setText("Please..Start the server");
        showStatistics();
        displayUsersLists();
        /*Timeline time = new Timeline();
        
        time.getKeyFrames().add(new KeyFrame(Duration.millis(3000),
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        displayUsersLists();  
                    }
            }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();*/    
    }
    
    @FXML
    /*start server button*/
    private void startServer(ActionEvent event) {
        System.out.println("run server!");
        try {

            if(registry == null)
            {
                registry = LocateRegistry.createRegistry(2000);
            }
                serverInstant=new ServerImpl();
                serverInstant.setServerController(this);
                registry.rebind("ChatService", serverInstant);
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
            serverInstant.clearClientsList();
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
    public void showStatistics(){
        /*Gender statistics part in piechart */
            //get ratios from database
            DBconnect db = DBconnect.getInstance();
            float males = db.countMales();
            float females = 100-males;
            ObservableList<PieChart.Data> details =  FXCollections.observableArrayList();
            details.addAll(new PieChart.Data("Male percentage",males) , new PieChart.Data("Female percentage",females));
            genderStatistic.setData(details);
            genderStatistic.setLabelsVisible(true);
            genderStatistic.setLegendSide(Side.TOP);
            /*Countries statistics part in bar chart*/
            xAxis.setLabel("Value");       
            yAxis.setLabel("Country"); 
            new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    XYChart.Series series1 = new XYChart.Series();
                    Map<String,Integer> myMap = db.countUsersPerCountry();
                    for(Map.Entry m:myMap.entrySet()){  
                        System.out.println(m.getKey()+" "+m.getValue());  
                        series1.getData().add(new XYChart.Data(m.getKey(),m.getValue()));
                }
                    countriesStatistic.getData().addAll(series1);
                }
            }.start();
    }
    public void displayUsersLists(){
        ObservableList<String> onlist = FXCollections.observableArrayList();
        ObservableList<String> oflist = FXCollections.observableArrayList();
        DBconnect db = DBconnect.getInstance();
        ArrayList<String> online = db.getOnlineUsers();
        ArrayList<String> offline = db.getOfflineUsers();
        /*check that list is not empty*/
        if(!online.isEmpty()){
            online.forEach((t)->{    
                onlist.add(t);
            });
            onlineList.setItems(onlist);
        }
        if(!offline.isEmpty()){
            offline.forEach((t)->{    
                oflist.add(t);
            });
            offlineList.setItems(oflist);
        }
    }
    
    public void sendAnnoncment(){
        String text = annText.getText();
        ServerImpl impl;
        try {
            impl = new ServerImpl();
            impl.sendNotification(text);
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
 
    public void closeButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
