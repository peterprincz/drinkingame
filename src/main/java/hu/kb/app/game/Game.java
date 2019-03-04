package hu.kb.app.game;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Player;

public interface Game {

    Integer getId();

    void addPlayer(Player player);

    void startGameRound() throws GameException;

    void sendAnswerToGameRound(Player player, Answer answer) throws GameException;

    GameRound getActiveGameRound();

    Result evaluateRound() throws GameException;

}
