package seng201.team019;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.gui.ScreenNavigator;
import seng201.team019.models.Car;
import seng201.team019.models.Difficulty;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameEnvironmentTest {

    private GameEnvironment gameEnvironment;

    @Mock
    private ScreenNavigator mockScreenNavigator;

    @Mock
    private Car mockCar1;

    @Mock
    private Car mockCar2;

    @BeforeEach
    public void setUp() {
        gameEnvironment = new GameEnvironment(mockScreenNavigator);
        gameEnvironment.setAvailableCars(new ArrayList<>(List.of(mockCar1, mockCar2)));
    }

    @Test
    public void testGameEnvironmentInitialization() {
        assertNotNull(gameEnvironment);

        gameEnvironment.completeGameEnvironmentSetup(Difficulty.Easy, 10, "name");

        assertEquals(Difficulty.Easy.getStartBalance(), gameEnvironment.getBankBalance());
        assertEquals(0, gameEnvironment.getRacesCompleted());
        assertEquals(2, gameEnvironment.getAvailableCars().size());
        assertEquals(3, gameEnvironment.getAvailableUpgrades().size());
        assertEquals(0, gameEnvironment.getGarage().size());
        assertEquals(10, gameEnvironment.getSeasonLength());
        assertEquals("name", gameEnvironment.getName());
        assertEquals(0, gameEnvironment.getOwnUpgrades().size());
    }

    @Test
    public void testBuyThenSellCar() {

        gameEnvironment.completeGameEnvironmentSetup(Difficulty.Easy, 10, "name");

        when(mockCar2.getPrice()).thenReturn(0d);
        assertTrue(gameEnvironment.buyCar(mockCar2));// have to have at least one car in garage

        double originalBankBalance = 100000.0;
        gameEnvironment.setBankBalance(originalBankBalance);

        int originalAvailableCars = gameEnvironment.getAvailableCars().size();

        assertTrue(gameEnvironment.buyCar(mockCar1));
        assertEquals(2, gameEnvironment.getGarage().size());
        assertEquals(originalAvailableCars - 1, gameEnvironment.getAvailableCars().size());
        assertEquals(originalBankBalance - mockCar1.getPrice(), gameEnvironment.getBankBalance(), 0.01);

        assertTrue(gameEnvironment.sellCar(mockCar1));
        assertEquals(1, gameEnvironment.getGarage().size());
        assertEquals(originalAvailableCars, gameEnvironment.getAvailableCars().size());
        assertEquals(originalBankBalance - mockCar1.getPrice() / 2, gameEnvironment.getBankBalance(), 0.01);
    }

}
