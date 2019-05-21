package hu.kb.app.controller.api;

import hu.kb.app.model.player.Gender;
import hu.kb.app.model.player.drinksetting.DrinkType;
import hu.kb.app.model.player.drinksetting.SipType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public
@Data
@ToString
@NoArgsConstructor
class CreatePlayerRequest {

    private String name;
    private Double weight;
    private SipType sipType;
    private DrinkType drinkType;
    private Gender gender;

}
