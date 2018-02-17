/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import iti.chat.common.ClientInter;
import iti.chat.common.Message;
import iti.chat.common.ServerInter;
import iti.chat.common.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import savechat.Creatxmlfile;

/**
 * FXML Controller class
 *
 * @author Hesham Kadry
 */
public class ChatBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Circle smallImageCircle1;
    Circle smallImageCircle2;
    
    Image imageSmallCirlce;
    Image imageSmallCirlce2;
    boolean isSender;
    
    /*variables added by Dina  */
    //
    //

    /*variables added by Hassna  */
    //
    //

    /*variables added by Hesham  */
        Color color;

    //

    /*variables added by Fatma  */
    //
    //
   private ArrayList<Message> messags = null;
    @FXML
    private Button ChatBox_Button_SaveChat;

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
    @FXML
    private ColorPicker ChatBox_ComboBox_ColorPicker;

    //
    private ClientInter mainClient = null;
    private ArrayList<ClientInter> recievers = null;
    private String usedTabID = null;
    private ServerInter server = null;

    //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //make scroll pan scrollable
        ChatBoxScrollPane.vvalueProperty().bind(ChatBox_AreaMessages.heightProperty());
        //setCircleMsg();
        smallImageCircle1 = new Circle(10, 10, 5);
        smallImageCircle1.setStroke(Color.SEAGREEN);
        
        smallImageCircle2 = new Circle(10, 10, 5);
        smallImageCircle2.setStroke(Color.SEAGREEN);
        
        initializeCompoBoxFontsType();
        initializeCompoBoxFontsSize();
        buttonImages();
        // TODO
        //listner color picker
        ChatBox_ComboBox_ColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("color picker value is "+ChatBox_ComboBox_ColorPicker.getValue());
                color = ChatBox_ComboBox_ColorPicker.getValue();
            }
        });
        
        //listner on text field on enter button
        ChatBox_TextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    ///////// these code is for testing purpose reblace it with yours                  
                    System.out.println("tmaaaaaaaaaaaaaaaaam");
                    Text data = new Text(getDate());
                    data.setFont(Font.font("", 10.0));
                    data.setFill(Color.DIMGREY);

                    Text t = new Text(ChatBox_TextField.getText());
                    //t.setFill(color);
                    TextFlow textFlow = new TextFlow(t, data);
                    //textFlow.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");
                    textFlow.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");

                    //imageSmallCirlce = new Image("/images/personal.png");
                    //smallImageCircle1.setFill(new ImagePattern(imageSmallCirlce));
                    HBox h = new HBox(textFlow);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ChatBox_AreaMessages.setSpacing(5);
                            h.setAlignment(Pos.BASELINE_RIGHT);
                            ChatBox_AreaMessages.getChildren().add(h);
//                            ChatBox_TextField.setText("");
                        }
                    });
                    //

                    try {
                        mainClient.getUser().setMessage(new Message(ChatBox_TextField.getText(), mainClient.getUser().getFullname(), usedTabID,usedTabID));
                        server.sendMessage(mainClient, recievers.get(0));
                    } catch (RemoteException ex) {
                        showAlert("Sorry the server currently is under maintenance");
                        Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //   
                }

            }

        });
        //

        recievers = new ArrayList<>();
        //
          messags = new ArrayList<>(); 
            ChatBox_Button_SaveChat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                try {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml files (*.xml)", "xml");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showSaveDialog(ChatBoxScrollPane.getScene().getWindow());
                    System.out.println(file);
                    
                   
                   Creatxmlfile.createFile(file,messags,mainClient.getUser());
                    
                } catch (IOException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
          
    }

    public void buttonImages() {
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

        //
        //code added by nagib
        //
    }

    public void Render(User user) {
        if (isSender) {
            Text data = new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);

            Text t = new Text(user.getMessage().getContent());

            TextFlow textFlow = new TextFlow(t, data);
            //textFlow.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");
            //imageSmallCirlce = new Image("/images/personal-website-design.png");
            //smallImageCircle.setFill(new ImagePattern(imageSmallCirlce));

            HBox h = new HBox(textFlow);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChatBox_AreaMessages.setSpacing(5);
                    h.setAlignment(Pos.BASELINE_RIGHT);
                    ChatBox_AreaMessages.getChildren().add(h);
                }
            });

        } else if (!isSender) {
            Text data = new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);

            Text t = new Text(user.getMessage().getContent());
            //t.setFill(color);
            TextFlow textFlow = new TextFlow(t, data);
             textFlow.setStyle("-fx-background-color: #FF8F00; -fx-background-radius: 25 0 25 25; -fx-padding: 5px; -fx-text-fill:#fff;");

            //imageSmallCirlce2 = new Image("/images/personal.png");
            //smallImageCircle2.setFill(new ImagePattern(imageSmallCirlce2));

            HBox h = new HBox(textFlow);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChatBox_AreaMessages.setSpacing(5);
                    ChatBox_AreaMessages.getChildren().add(h);
                }
            });
        }
        //

        //code added by nagib
        //
    }
        
    public void sendMessage(User user) {
        isSender = true;
        Render(user);
        //

        //code added by nagib
        //
               messags.add(user.getMessage());
    }

    public void recieveMessage(User user) {
        isSender = false;
        Render(user);
        //

        //code added by nagib
        //
        messags.add(user.getMessage());
    }

    public String getDate() {
        Date date = new Date();
        String strDateFormat = " hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
        return formattedDate;
        //

        //code added by nagib
        //
    }

    public void setCircleMsg() {
        //initialize the node circle
        
        //

        //code added by nagib
        //
    }

    public void initializeCompoBoxFontsType() {
        ChatBox_ComboBox_FontType.getItems().removeAll(ChatBox_ComboBox_FontType.getItems());
        ChatBox_ComboBox_FontType.getItems().addAll("normal", "Verdana", "Serif Bold", "Arial");
        ChatBox_ComboBox_FontType.getSelectionModel().select("font type");
        //

        //code added by nagib
        //
    }

    public void initializeCompoBoxFontsSize() {
        ChatBox_ComboBox_FontSize.getItems().removeAll(ChatBox_ComboBox_FontSize.getItems());
        ChatBox_ComboBox_FontSize.getItems().addAll("8", "14", "18", "22");
        ChatBox_ComboBox_FontSize.getSelectionModel().select("size");
        //

        //code added by nagib
        //
    }

    //
    public void setMainClient(ClientInter mainClient) {
        this.mainClient = mainClient;
    }

    public void setReciever(ClientInter reciever) {
        if (this.recievers == null) {
            this.recievers = new ArrayList<>();
        }
        this.recievers.add(reciever);
    }

    public void setUsedTab(String usedTab) {
        this.usedTabID = usedTab;
    }

    public ServerInter getServer() {
        return server;
    }

    public void setServer(ServerInter server) {
        this.server = server;
    }

    public ArrayList<ClientInter> getRecievers() {
        return recievers;
    }
    
    

    @FXML
    private void send(ActionEvent event) {

        System.out.println("sending");
        ChatBox_TextField.setText("");
    }
     private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }

    //
    
     /*Methods added by Dina  */
    //
    @FXML
    public void attachFile(ActionEvent event){
        Stage st =(Stage) ((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog(st);
        if (f == null) {return;}
           Thread transfer = new Thread(()->{
                try {
                        if(this.recievers.get(0).getTransferFile().askForAcceptance(this.recievers.get(0)))
                        {
                            this.mainClient.getTransferFile().sendFile(this.recievers.get(0), f);
                        } 
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }); 
           transfer.start();

    }
    /*Methods added by Hassna  */
    //
    /*Methods added by Hesham  */
    //
    /*Methods added by Fatma  */
    //
      
}
