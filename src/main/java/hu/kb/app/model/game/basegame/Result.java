package hu.kb.app.model.game.basegame;

import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor class Result {

    private String result;
    private List<Player> winners = new ArrayList<>();
    private List<Player> losers = new ArrayList<>();
    private boolean lastQuestion = false;

    public Result(String result){
        this.result = result;
    }

    public void addWinner(Player player){
        winners.add(player);
    }

    public void addLoser(Player player){
        losers.add(player);
    }
}