package hu.kb.app.game.gamecycle;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

public interface GameCycle {
    void join(Player player);
    Question start();
    void handleAnswer(Player player, Answer answer);
    Result evaluateResults();
    void setStatus(Status status);
    Status getStatus();
}
