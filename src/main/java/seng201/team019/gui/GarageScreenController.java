package seng201.team019.gui;

import seng201.team019.GameEnvironment;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class GarageScreenController extends ScreenController {

    /**
     * Initialize the window
     */
    public void initialize() {
        // TODO: Add any initialization code here
    }

    public GarageScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/garage.fxml";
    }

    @Override
    protected String getTitle() {
        return "Garage";
    }
}
