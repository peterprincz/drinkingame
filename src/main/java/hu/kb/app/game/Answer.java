package hu.kb.app.game;

import hu.kb.app.player.Player;

public class Answer {
    private Player player;
    private String answer;

    public Answer(Player player, String answer) {
        this.player = player;
        this.answer = answer;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAnswer() {
        return answer;
    }
}
