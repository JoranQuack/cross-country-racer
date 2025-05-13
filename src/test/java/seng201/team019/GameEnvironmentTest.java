package seng201.team019;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import seng201.team019.gui.ScreenNavigator;
import seng201.team019.models.Car;
// import seng201.team019.models.Upgrade;
import seng201.team019.models.Difficulty;

public class GameEnvironmentTest {

    private GameEnvironment gameEnvironment;

    private ScreenNavigator mockScreenNavigator;
    private Car mockCar;
    // private Upgrade mockUpgrade;

    @BeforeEach
    public void setUp() {
        mockScreenNavigator = Mockito.mock(ScreenNavigator.class);
        gameEnvironment = new GameEnvironment(mockScreenNavigator);
    }

    @Test
    public void testGameEnvironmentInitialization() {
        assertNotNull(gameEnvironment);

        assertEquals(200000.0, gameEnvironment.getBankBalance(), 0.01);
        assertEquals(0, gameEnvironment.getRacesCompleted());
        assertEquals(5, gameEnvironment.getAvailableCars().size());
        assertEquals(5, gameEnvironment.getAvailableParts().size());
        assertEquals(0, gameEnvironment.getGarage().size());
        assertEquals(0, gameEnvironment.getOwnUpgrades().size());
    }

    @Test
    public void testCompleteGameEnvironmentSetup() {
        gameEnvironment.completeGameEnvironmentSetup(Difficulty.EASY, 10, "TestPlayer");

        assertEquals(Difficulty.EASY, gameEnvironment.getDifficulty());
        assertEquals("TestPlayer", gameEnvironment.getName());
        // TODO: Implement this method
        // assertEquals(10, gameEnvironment.getSeasonLength());
    }

    @Test
    public void testBuyThenSellCar() {
        gameEnvironment.initializeAvailableCars();
        mockCar = gameEnvironment.getAvailableCars().get(0);
        Double originalBankBalance = 100000.0;
        gameEnvironment.setBankBalance(originalBankBalance);

        assertTrue(gameEnvironment.buyCar(mockCar));

        assertEquals(1, gameEnvironment.getGarage().size());
        assertEquals(4, gameEnvironment.getAvailableCars().size());
        assertEquals(originalBankBalance - mockCar.getPrice(), gameEnvironment.getBankBalance(), 0.01);

        assertTrue(gameEnvironment.sellCar(mockCar));
        assertEquals(0, gameEnvironment.getGarage().size());
        assertEquals(5, gameEnvironment.getAvailableCars().size());
        assertEquals(originalBankBalance - mockCar.getPrice() / 2, gameEnvironment.getBankBalance(), 0.01);
    }

}
