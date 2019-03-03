package hu.kb.app.game;

import hu.kb.app.player.Gender;
import hu.kb.app.player.drinksetting.DrinkType;
import hu.kb.app.service.AlcoholCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlcoholCalculatorTest {

    private AlcoholCalculatorService alcoholCalculatorService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        alcoholCalculatorService = new AlcoholCalculatorService();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        alcoholCalculatorService = null;
    }

    @Test
    public void testTwoWhiskeyShot(){
        Double firstDrink = alcoholCalculatorService.calculateBAC(20.0, DrinkType.WHISKEY, 80.0, Gender.MALE);
        Double secondDrink = alcoholCalculatorService.calculateBAC(40.0, DrinkType.WHISKEY, 80.0, Gender.MALE);
        assertTrue(secondDrink > firstDrink);
    }
}
