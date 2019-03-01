package hu.kb.app.service;

import hu.kb.app.player.Gender;
import hu.kb.app.player.drinksetting.DrinkType;
import org.springframework.stereotype.Service;

@Service
public class AlcoholCalculatorService {


    private static final Double WILDMARK_FEMALE_FACTOR= 0.55;
    private static final Double WILDMARK_MALE_FACTOR = 0.68;
    private static final Double ETHANOL_DENSITY = 0.789;

    public Double calculateBAC(Double totalAlcohol, DrinkType drinkType, Double weight, Gender gender, Integer hourPassed){

        Double alcoholInGramm = totalAlcohol / drinkType.getAlcoholPrecentage() * ETHANOL_DENSITY;
        if(Gender.MALE.equals(gender)){
            return (alcoholInGramm * 5.14/weight * WILDMARK_MALE_FACTOR) - .015 * hourPassed;
        } else {
            return (alcoholInGramm * 5.14/weight * WILDMARK_FEMALE_FACTOR) - .015 * hourPassed;
        }
    }

    public Double calculateBAC(Double totalAlcohol, DrinkType drinkType,  Double weight, Gender gender){
        return calculateBAC(totalAlcohol, drinkType, weight, gender, 1);
    }

}
