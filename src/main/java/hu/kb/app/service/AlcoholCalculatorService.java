package hu.kb.app.service;

import hu.kb.app.player.drinksetting.DrinkType;
import org.springframework.stereotype.Service;

@Service
public class AlcoholCalculatorService {


    private static final Double WILDMARK_FEMALE_FACTOR= 0.55;
    private static final Double WILDMARK_MALE_FACTOR = 0.68;
    private static final Double ETHANOL_DENSITY = 0.789;

    public Double calculateBAC(Double totalAlcohol, DrinkType drinkType,  Double weight, String gender, Integer hourPassed){

        Double alcoholInGramm = totalAlcohol / drinkType.getAlcoholPrecentage() * ETHANOL_DENSITY;

        if("MALE".equalsIgnoreCase(gender)){
            return (alcoholInGramm * 5.14/weight * WILDMARK_MALE_FACTOR) - .015 * hourPassed;
        }
        if("FEMALE".equalsIgnoreCase(gender)) {
            return (alcoholInGramm * 5.14/weight * WILDMARK_FEMALE_FACTOR) - .015 * hourPassed;
        }

        throw new RuntimeException("INVALID GENDER :" + gender);
    }

    public Double calculateBAC(Double totalAlcohol, DrinkType drinkType,  Double weight, String gender){

        Double alcoholInGramm = totalAlcohol / drinkType.getAlcoholPrecentage() * ETHANOL_DENSITY;

        if("MALE".equalsIgnoreCase(gender)){
            return (alcoholInGramm * 5.14/weight * WILDMARK_MALE_FACTOR) - .015 * 1;
        }
        if("FEMALE".equalsIgnoreCase(gender)) {
            return (alcoholInGramm * 5.14/weight * WILDMARK_FEMALE_FACTOR) - .015 * 1;
        }

        throw new RuntimeException("INVALID GENDER :" + gender);
    }

}
