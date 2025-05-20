package seng201.team019.gui;

import seng201.team019.GameEnvironment;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class that starts the JavaFX application thread.
 *
 * @author seng201 teaching team
 * @author Joran Le Quellec
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
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Car Game");
        primaryStage.show();

        new GameEnvironment(new ScreenNavigator(primaryStage));
    }
}
