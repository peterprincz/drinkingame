package hu.kb.app.game.quiz;

import hu.kb.app.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String result;
    private List<Player> winners = new ArrayList<>();
    private List<Player> losers = new ArrayList<>();

    public String getResult() {
        return result;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getLosers() {
        return losers;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void setLosers(List<Player> losers) {
        this.losers = losers;
    }
}