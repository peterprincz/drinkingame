package hu.kb.app.api;

import hu.kb.app.player.drinksetting.DrinkType;
import hu.kb.app.player.drinksetting.SipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data class EditPlayerRequest {
    private Integer playedId;
    private SipType sipType;
    private DrinkType drinkType;

}
