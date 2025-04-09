package seng201.team019.gui;

import seng201.team019.GameEnvironment;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class PlayingScreenController extends ScreenController {

    /**
     * Initialize the window
     */
    public void initialize() {
        // TODO: Add any initialization code here
    }

    public PlayingScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/playing.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }
}
