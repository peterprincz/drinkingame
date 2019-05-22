package hu.kb.app.service;

import hu.kb.app.model.player.Gender;
import hu.kb.app.model.player.drinksetting.DrinkType;
import org.springframework.stereotype.Service;

@Service
public class AlcoholCalculatorService {


    private static final Double WILDMARK_FEMALE_FACTOR= 0.55;
    private static final Double WILDMARK_MALE_FACTOR = 0.68;

    public Double calculateBAC(Double totalAlcohol, DrinkType drinkType, Double weight, Gender gender, Integer hourPassed){
        double alcholInOz = totalAlcohol / 28.34;
        double weightInLbs = weight * 2.20;
        Double genderFactor;
        if(Gender.MALE.equals(gender)){
            genderFactor = WILDMARK_MALE_FACTOR;
        } else {
            genderFactor = WILDMARK_FEMALE_FACTOR;
        }
        return (alcholInOz * 5.14 / weightInLbs  * genderFactor) - 0.015 * hourPassed / 60;
    }

    public Double calculateBAC(Double totalAlcohol, DrinkType drinkType,  Double weight, Gender gender){
        return calculateBAC(totalAlcohol, drinkType, weight, gender, 1);
    }

}
