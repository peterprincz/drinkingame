package hu.kb.app.model.game.basegame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.game.basegame.Answer;
import hu.kb.app.model.game.basegame.Question;
import hu.kb.app.model.game.basegame.Result;
import hu.kb.app.model.player.Player;

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
