package seng201.team019.gui;

import seng201.team019.GameEnvironment;

/**
 * Controller for the setup.fxml window
 */
public class SetupScreenController extends ScreenController {

    /**
     * Initialize the window
     */
    public void initialize() {
        // TODO: Add any initialization code here
    }

    public SetupScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/setup.fxml";
    }

    @Override
    protected String getTitle() {
        return "Setup Screen";
    }
}
