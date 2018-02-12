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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Hesham Kadry
 */
public class ChatBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Circle smallImageCircle;
    Image imageSmallCirlce;
    boolean isSender;
    @FXML
    private Button ChatBox_Button_AddFirendToChat;
    @FXML
    private Button ChatBox_Button_AttachFile;
    @FXML
    private Button ChatBox_Button_Email;
    @FXML
    private VBox ChatBox_AreaMessages;
    @FXML
    private TextField ChatBox_TextField;
    @FXML
    private ScrollPane ChatBoxScrollPane;
    @FXML
    private ComboBox ChatBox_ComboBox_FontType;
    @FXML
    private ComboBox ChatBox_ComboBox_FontSize;
    
    //
    
    //attrs added by nagib
    
    
    //
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //make scroll pan scrollable
        ChatBoxScrollPane.vvalueProperty().bind(ChatBox_AreaMessages.heightProperty());
        setCircleMsg();
        initializeCompoBoxFontsType();
        initializeCompoBoxFontsSize();
        buttonImages();
        // TODO
        
        //listner on text field on enter button
        ChatBox_TextField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) {
                
                
                if (event.getCode() == KeyCode.ENTER) {
                    ///////// these code is for testing purpose reblace it with yours
                       System.out.println("tmaaaaaaaaaaaaaaaaam");
                              Text data= new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);
            
            Text t = new Text(ChatBox_TextField.getText());
            
            TextFlow textFlow = new TextFlow(t,data);
            textFlow.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");
           
            imageSmallCirlce = new Image("/images/personal-website-design.png");
            smallImageCircle.setFill(new ImagePattern(imageSmallCirlce));
            HBox h = new HBox(textFlow,smallImageCircle);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChatBox_AreaMessages.setSpacing(5);
                    h.setAlignment(Pos.BASELINE_RIGHT);
                    ChatBox_AreaMessages.getChildren().add(h);
                    ChatBox_TextField.setText("");
                }
            });
                    
                    
                    }
                
            }
           
        });
    } 
    
    public void buttonImages()
    {
        File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\ChatProject\\client\\src\\main\\resources\\images\\003-group-.png");
        Image image = new Image(file.toURI().toString());
        ChatBox_Button_AddFirendToChat.setGraphic(new ImageView(image));
        
        //File attach = new File("/images/attach-icon.png");
        File attach = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\ChatProject\\client\\src\\main\\resources\\images\\004-external.png");
        Image imgAttach = new Image(attach.toURI().toString());
        ChatBox_Button_AttachFile.setGraphic(new ImageView(imgAttach));
        
        //File email = new File("/images/icon-email-128.png");
        File email = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\ChatProject\\client\\src\\main\\resources\\images\\note.png");
        Image imgEmail = new Image(email.toURI().toString());
        ChatBox_Button_Email.setGraphic(new ImageView(imgEmail));
        
    }
    
    public void Render(User user)
    {
        if(isSender)
        {
            Text data= new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);
            
            Text t = new Text(user.getMessage().getContent());
            
            TextFlow textFlow = new TextFlow(t,data);
            textFlow.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");
            imageSmallCirlce = new Image("/images/personal-website-design.png");
            smallImageCircle.setFill(new ImagePattern(imageSmallCirlce));
            
            HBox h = new HBox(textFlow,smallImageCircle);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChatBox_AreaMessages.setSpacing(5);
                    h.setAlignment(Pos.BASELINE_RIGHT);
                    ChatBox_AreaMessages.getChildren().add(h);
                }
            });
            
        }else if(!isSender)
        {
            Text data= new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);
            
            Text t = new Text(user.getMessage().getContent());
            
            TextFlow textFlow = new TextFlow(t,data);
            textFlow.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");
            imageSmallCirlce = new Image("/images/personal-website-design.png");
            smallImageCircle.setFill(new ImagePattern(imageSmallCirlce));
            
            HBox h = new HBox(smallImageCircle,textFlow);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChatBox_AreaMessages.setSpacing(5);
                    ChatBox_AreaMessages.getChildren().add(h);
                }
            });
        }
        
    }
    
    public void sendMessage(User user)
    {
        isSender = true;
        Render(user);
        
    }
    
    public void recieveMeddage(User user)
    {
        isSender = false;
        Render(user);
    }

    public String getDate()
    {
        Date date = new Date();
        String strDateFormat = " hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate); 
        return formattedDate;
    }
    
    public void setCircleMsg()
    {
        //initialize the node circle
        smallImageCircle = new Circle(10,10,5);
        smallImageCircle.setStroke(Color.SEAGREEN);
    }
    
    public void initializeCompoBoxFontsType() {
        ChatBox_ComboBox_FontType.getItems().removeAll(ChatBox_ComboBox_FontType.getItems());
        ChatBox_ComboBox_FontType.getItems().addAll("normal", "Verdana", "Serif Bold", "Arial");
        ChatBox_ComboBox_FontType.getSelectionModel().select("font type");
    }
    public void initializeCompoBoxFontsSize() {
        ChatBox_ComboBox_FontSize.getItems().removeAll(ChatBox_ComboBox_FontSize.getItems());
        ChatBox_ComboBox_FontSize.getItems().addAll("8", "14", "18", "22");
        ChatBox_ComboBox_FontSize.getSelectionModel().select("size");
    }
    
    
    //
    
    //methods added by nagib
    
    
    //

}
