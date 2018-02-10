package client.models;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    Scene scene;

    @Override
    public void start(final Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        FXMLLoader loader2 = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/fxml/Scene.fxml").openStream());
        Parent root2 = loader2.load(getClass().getResource("/fxml/SplashScreen.fxml").openStream());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //movable pane
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
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        scene = new Scene(root2);
        scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
        PauseTransition splashScreenDelay = new PauseTransition(Duration.seconds(3));
        splashScreenDelay.setOnFinished(f -> {
            primaryStage.hide();
            System.out.println("working");
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toString());
//            stage.initStyle(StageStyle.DECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("seen");

        });
        splashScreenDelay.playFromStart();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
