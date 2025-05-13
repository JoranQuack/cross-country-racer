package seng201.team019.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seng201.team019.GameEnvironment;

/**
 * Abstract parent class for all {@link GameEnvironment} UI controller classes.
 * 
 * @author seng201 teaching team
 */
public abstract class ScreenController {

    private final GameEnvironment gameEnvironment;

    /**
     * Creates an instance of a ScreenController with the given
     * {@link GameEnvironment}
     * 
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected ScreenController(final GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Shows an alert dialog to the user
     * 
     * @param type    the type of alert (ERROR, INFORMATION, etc.)
     * @param title   the title of the alert
     * @param content the content message
     */
    public void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setContentText(content);

        alert.setHeaderText(null);
        alert.setGraphic(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/global.css").toExternalForm());

        alert.showAndWait();
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    protected abstract String getTitle();

    /**
     * Gets the rocket manager associated with this screen controller.
     * 
     * @return The rocket manager for this controller
     */
    protected GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }
}
