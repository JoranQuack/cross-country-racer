package seng201.team019.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.services.CounterService;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class MainScreenController extends ScreenController {

    @FXML
    private Label defaultLabel;

    @FXML
    private Button defaultButton;

    private CounterService counterService;

    /**
     * Initialize the window
     */
    public void initialize() {
        counterService = new CounterService();
    }

    public MainScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Method to call when our counter button is clicked
     */
    @FXML
    public void onButtonClicked() {
        System.out.println("Button has been clicked");
        counterService.incrementCounter();

        int count = counterService.getCurrentCount();
        defaultLabel.setText(Integer.toString(count));
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/main.fxml";
    }

    @Override
    protected String getTitle() {
        return "Main Screen";
    }
}


