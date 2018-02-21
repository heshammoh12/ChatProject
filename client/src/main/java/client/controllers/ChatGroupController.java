package client.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import iti.chat.common.User;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Hesham Kadry
 */
public class ChatGroupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private User loginer ;
    ArrayList<User>groupUser = new ArrayList<>();
    @FXML
    private Button Button_GroupChar;
    @FXML
    private ListView ListView_GroupChatAllUsers;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public class ListFormat extends ListCell<User>
    {
        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            
            if(item!=null && !empty)
            {
            HBox pictureRegion = new HBox();
            HBox chBox = new HBox();
            CheckBox cb1 = new CheckBox();
            
            Text text = new Text(item.getEmail());
            File file = new File("C:\\Users\\Hesham Kadry\\Documents\\NetBeansProjects\\CustomList\\src\\customlist\\personal-website-design.png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            
            cb1.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                       System.out.println(item.getEmail()); 
                       groupUser.add(item);
                }
            });
            /*
            pictureRegion.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                       System.out.println("presseeeeed");
                       System.out.println(item.phone);
                }
            });
            */
            chBox.getChildren().add(cb1);
            chBox.setAlignment(Pos.CENTER_RIGHT);
            pictureRegion.getChildren().add(imageView);
            pictureRegion.getChildren().add(text);
            pictureRegion.getChildren().add(chBox);
            pictureRegion.setHgrow(chBox, Priority.ALWAYS);
            setGraphic(pictureRegion);
            }else{
                setGraphic(null);
            }
            
        }       
    }
    
}
