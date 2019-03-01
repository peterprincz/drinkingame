package hu.kb.app.api;

import hu.kb.app.player.Gender;
import hu.kb.app.player.drinksetting.DrinkType;
import hu.kb.app.player.drinksetting.SipType;
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
