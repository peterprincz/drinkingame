package hu.kb.app.game.model;

import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor class Result {

    private String result;
    private List<Player> winners = new ArrayList<>();
    private List<Player> losers = new ArrayList<>();
    private boolean lastQuestion = false;

}