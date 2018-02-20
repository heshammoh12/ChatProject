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
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
    boolean acceptFile=false;
    //

    /*variables added by Hassna  */
    //
    //

    /*variables added by Hesham  */
        Color color=Color.BLACK;
        String colorString = "#000000";
        String userLabel;
        String userLabel2;
        String fontType="Arial";
        String fontSize="8"; 
        LocalDate loc;
        
        //LocalDate localDate_1;
        //DateTimeFormatter formatter_1;

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
    private File f = null;

    //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //formatter_1=DateTimeFormatter.ofPattern("dd-MMM-yyyy");
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
                System.out.println("color picker value is " + ChatBox_ComboBox_ColorPicker.getValue());
                color = ChatBox_ComboBox_ColorPicker.getValue();
                colorString ="#" + Integer.toHexString(color.hashCode()); 
                ChatBox_TextField.setStyle("-fx-text-inner-color:" + colorString + ";");
                
            }
        });

        //listner on text field on enter button
        ChatBox_TextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    ///////// these code is for testing purpose reblace it with yours  
                    /*
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
                     */   
                    fontType = ChatBox_ComboBox_FontType.getValue().toString();
                    fontSize = ChatBox_ComboBox_FontSize.getValue().toString();
                    
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = formatter.parse("2018-02-10");
                        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
                        cal.setTime(date);
                        XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
                        loc = result.toGregorianCalendar().toZonedDateTime().toLocalDate();
                        System.out.println("locaaal date is "+loc);
                        
                        
                        
                        mainClient.getUser().setMessage(new Message(ChatBox_TextField.getText(), mainClient.getUser().getFullname(), usedTabID,colorString,fontType,loc,fontSize));
                        //mainClient.getUser().setMessage(new Message(ChatBox_TextField.getText(), mainClient.getUser().getFullname(), usedTabID,usedTabID));
                        System.out.println("main client issssssss "+mainClient.getUser().getFullname());
                        server.sendMessage(mainClient, recievers.get(0));
                        sendMessage(mainClient.getUser());
                    } catch (RemoteException ex) {
                        showAlert("Sorry the server currently is under maintenance");
                        Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DatatypeConfigurationException ex) {
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

                    Creatxmlfile.createFile(file, messags, mainClient.getUser());

                } catch (IOException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    @FXML
    private void comboActionFontType(ActionEvent event) {
    System.out.println(ChatBox_ComboBox_FontType.getValue());
    ChatBox_TextField.setFont(Font.font(ChatBox_ComboBox_FontType.getValue().toString()));
    }
    public void buttonImages() {
//        File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\ChatProject\\client\\src\\main\\resources\\images\\003-group-.png");
        
        try {
            Image image = new Image(this.getClass().getResource("/images/group.png").toURI().toString());
            ImageView image1 =new ImageView(image);
            image1.setFitWidth(20);image1.setFitHeight(20);      
            ChatBox_Button_AddFirendToChat.setGraphic(image1);

            //File attach = new File("/images/attach-icon.png");
//            File attach = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\ChatProject\\client\\src\\main\\resources\\images\\004-external.png");
            Image imgAttach = new Image(this.getClass().getResource("/images/external.png").toURI().toString());
            ImageView imgAttach1 =new ImageView(imgAttach);
            imgAttach1.setFitWidth(20);imgAttach1.setFitHeight(20); 
            ChatBox_Button_AttachFile.setGraphic(imgAttach1);
            
            
            Image imgSave = new Image(this.getClass().getResource("/images/Download.png").toURI().toString());
            ImageView imgSave1 =new ImageView(imgSave);
            imgSave1.setFitWidth(20);imgSave1.setFitHeight(20); 
            ChatBox_Button_SaveChat.setGraphic(imgSave1);

            //File email = new File("/images/icon-email-128.png");
//            File email = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\ChatProject\\client\\src\\main\\resources\\images\\note.png");
            Image imgEmail = new Image(this.getClass().getResource("/images/note.png").toURI().toString());
        } catch (URISyntaxException ex) {
            Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ChatBox_Button_Email.setGraphic(new ImageView(imgEmail));

        //
        //code added by nagib
        //
    }

    public void Render(User user) {
        
        if (isSender) 
        {
            
            try {
                System.out.println("sender name issssssssssss  "+mainClient.getUser().getFullname());
                //userLabel = mainClient.getUser().getFullname();
                userLabel2= mainClient.getUser().getFullname();
                /*
                Text t = new Text(userLabel);
                t.setFill(Color.DARKGRAY);
                HBox v = new HBox(t);
                v.setAlignment(Pos.BASELINE_RIGHT);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ChatBox_AreaMessages.getChildren().add(v);
                    }
                });
                
                */
            } catch (RemoteException ex) {
                Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Text data = new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);

            Text t = new Text(user.getMessage().getContent());
            t.setStyle("-fx-fill: "+user.getMessage().getColor()+";");
            //t.setFill(user.getMessage().getColor());
            System.out.println("color is here in string before style "+user.getMessage().getColor());
           // t.setStyle("-fx-text-fill:" + user.getMessage().getColor() + ";");    
           //t.setFill(color.AQUAMARINE);
            try {
                NumberFormat nf = NumberFormat.getInstance();
                double number = nf.parse(user.getMessage().getFontSize()).doubleValue();
                t.setFont(Font.font (user.getMessage().getFont(), number));
            } catch (ParseException ex) {
                Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Double size = Double.parseDouble(user.getMessage().getFontSize());
            
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
   //                 ChatBox_TextField.setText("");
                }
            });

        }else if (!isSender) {
             
            try {
                if(!user.getFullname().equals(userLabel2)&& !user.getFullname().equals(mainClient.getUser().getFullname()))   
                {
                    System.out.println("sender name issssssssssss  "+user.getFullname());
                    userLabel2 = user.getFullname();
                    Text t2 = new Text(userLabel2);
                    t2.setFill(Color.DARKGRAY);
                    HBox v2 = new HBox(t2); 
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                             ChatBox_AreaMessages.getChildren().add(v2);
                        }
                    });
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
            }

                
            
            System.out.println("reciever name issssssssss  "+user.getFullname());
            Text data = new Text(getDate());
            data.setFont(Font.font("", 10.0));
            data.setFill(Color.DIMGREY);

            Text t = new Text(user.getMessage().getContent());
            //t.setFill(user.getMessage().getColor());
            t.setStyle("-fx-fill: "+user.getMessage().getColor()+";");
            System.out.println("color is here in string before style222222 "+user.getMessage().getColor());
            
            ///t.setStyle("-fx-text-fill:" + user.getMessage().getColor() + ";");
            try{
                NumberFormat nf = NumberFormat.getInstance();
                double number2 = nf.parse(user.getMessage().getFontSize()).doubleValue();
                t.setFont(Font.font (user.getMessage().getFont(), number2));
                
            }catch (ParseException ex) {
                Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
        System.out.println("send message");
        //

        //code added by nagib
        //
        messags.add(user.getMessage());
    }

    public void recieveMessage(User user) {
        System.out.println("reciev messgggggg");
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
    /*
    public LocalDate getLocalDate()
    {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse("2018-02-10");
            GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
            cal.setTime(date);
            XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            System.out.println("locaaal date is "+result);
            
        } catch (ParseException ex) {
            Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
*/
    public void setCircleMsg() {
        //initialize the node circle

        //
        //code added by nagib
        //
    }

    public void initializeCompoBoxFontsType() {
        ChatBox_ComboBox_FontType.getItems().removeAll(ChatBox_ComboBox_FontType.getItems());
        ChatBox_ComboBox_FontType.getItems().addAll("normal", "Verdana", "Serif Bold", "Arial" ,"Algerian","Chiller");
        ChatBox_ComboBox_FontType.getSelectionModel().select("font type");
        //

        //code added by nagib
        //
    }

    public void initializeCompoBoxFontsSize() {
        ChatBox_ComboBox_FontSize.getItems().removeAll(ChatBox_ComboBox_FontSize.getItems());
        ChatBox_ComboBox_FontSize.getItems().addAll("8", "14", "18", "22");
        ChatBox_ComboBox_FontSize.getSelectionModel().select("14");
        //

        //code added by nagib
        //
    }

    public void requestRecieveFile(ClientInter sender) {
        System.out.println("requestRecieveFile");
        try {
            System.out.println("the main clint is + " + mainClient.getUser().getFullname());
        } catch (RemoteException ex) {
            Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Do you want to download file ?");
            alert.setContentText(" ");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("ok");
                acceptFile = true;
                try {
                    sender.startSendingFile(usedTabID);
                    
                        //this.mainClient.getTransferFile().sendFile(this.recievers.get(0), f);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("cancel");
                acceptFile = false;
            }

        });
    }
    public void startSendingFile(){
        System.out.println("ChatBoxController startSendingFile()");
        try {
            this.mainClient.getTransferFile().sendFile(this.recievers.get(0), f);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
    public void attachFile(ActionEvent event) {
        f = null;
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        f = fileChooser.showOpenDialog(st);
        if (f != null) {
            Thread transfer = new Thread(() -> {
                try {
                    System.out.println("the sender of the file is : " + this.mainClient.getUser().getFullname());
                    System.out.println("the reciever of the file is : " + this.recievers.get(0).getUser().getFullname());
                    this.recievers.get(0).acceptRecieveingFile(mainClient,usedTabID);
                    System.out.println("accept file is " +acceptFile );
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            transfer.start();
        }
    }
    /*Methods added by Hassna  */
    //
    /*Methods added by Hesham  */
    //
    /*Methods added by Fatma  */
    //

}
