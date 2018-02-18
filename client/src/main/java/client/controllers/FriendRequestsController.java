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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 *
 * @author Dina PC
 */
public class FriendRequestsController implements Initializable{

    private User loginer;

    public void setLoginer(User loginer) {
        this.loginer = loginer;
        System.out.println("this + "+this);
    }

    public User getLoginer() {
        return loginer;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public class ListFormat extends ListCell<User> 
    {
        @Override
        protected void updateItem(User item, boolean empty) 
        {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            if (item != null && !empty) 
            {
                HBox hboxButton = new HBox();
                HBox pictureRegion = new HBox();
                Text text = new Text(item.getEmail());
                File file = new File("\\resources\\images\\personal.png");
                Button acceptBtn = new Button("Ok");
                Button rejectBtn = new Button("X");
                //accept the friend request
                acceptBtn.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event) 
                    {
                       
                    }
                });
                
                //reject the friend request
                rejectBtn.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event) 
                    {
                       
                    }
                });
                
                hboxButton.getChildren().add(acceptBtn);
                hboxButton.getChildren().add(rejectBtn);
                hboxButton.setAlignment(Pos.CENTER_RIGHT);
                pictureRegion.setHgrow(hboxButton, Priority.ALWAYS);
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                pictureRegion.getChildren().add(imageView);
                pictureRegion.getChildren().add(text);
                pictureRegion.getChildren().add(hboxButton);
                pictureRegion.setPadding(new Insets(2));
                setGraphic(pictureRegion);
            }else {
                setGraphic(null);
            }

        }
    }
}
