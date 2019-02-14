package hu.kb.app.game.gamecycle;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.player.Player;

public interface GameCycle {
    void join(Player player);
    String start();
    void handleAnswer(Answer answer);
    String evaluateResults();
}
