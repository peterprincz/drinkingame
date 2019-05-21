package hu.kb.app.game;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.game.enums.Status;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Player;

import java.util.List;

public interface GameRound {

    void join(Player player);
    Question start() throws GameException;
    void handleAnswer(Player player, Answer answer) throws GameException;
    Result evaluateResults() throws GameException;

    Status getStatus();
    void setStatus(Status status);
    Question getQuestion();
    void setQuestion(Question question);
}
