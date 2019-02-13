package hu.kb.app.game;

import hu.kb.app.player.Player;

public interface GameCycle {
    void join(Player player);
    Question start();
    void handleAnswer(Answer answer);
    String evaluateResults();
}
