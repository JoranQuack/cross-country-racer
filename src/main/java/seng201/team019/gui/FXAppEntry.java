package seng201.team019.gui;

import seng201.team019.GameEnvironment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class that starts the JavaFX application thread.
 * 
 * @author seng201 teaching team
 */
public class FXAppEntry extends Application {

    /**
     * Creates the {@link GameEnvironment} with a {@link ScreenNavigator} for the
     * given {@link Stage}
     * 
     * @param primaryStage The current fxml stage, handled by this JavaFX
     *                     Application class
     */
    @Override
    public void start(Stage primaryStage) {
        // Load the icon image
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));

        // Set the icon on the stage
        primaryStage.getIcons().add(icon);

        // Create a scene (example)
        Scene scene = new Scene(new Label("Hello World!"), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My JavaFX App");

        // Show the stage
        primaryStage.show();
        new GameEnvironment(new ScreenNavigator(primaryStage));
    }
}
