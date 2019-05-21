package hu.kb.app.controller.api;

import hu.kb.app.model.player.drinksetting.DrinkType;
import hu.kb.app.model.player.drinksetting.SipType;
import lombok.Data;

public @Data class EditPlayerRequest {
    private Integer playedId;
    private SipType sipType;
    private DrinkType drinkType;

}
