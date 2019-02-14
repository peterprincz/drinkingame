package hu.kb.app.game.quiz;

import hu.kb.app.player.Player;


public class Answer {

    private Integer gameId;
    private Player player;
    private String answer;

    public Answer(){}

    public Answer(Integer gameId, Player player, String answer) {
        this.gameId = gameId;
        this.player = player;
        this.answer = answer;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
