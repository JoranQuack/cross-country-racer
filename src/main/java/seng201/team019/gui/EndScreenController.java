package seng201.team019.gui;

import seng201.team019.GameEnvironment;

/**
 * Controller for the end.fxml window
 */
public class EndScreenController extends ScreenController {

    public EndScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        // TODO: Add any initialization code here
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/end.fxml";
    }

    @Override
    protected String getTitle() {
        return "End Screen";
    }
}
