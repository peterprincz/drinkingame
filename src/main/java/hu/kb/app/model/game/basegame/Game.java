package hu.kb.app.model.game.basegame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.model.player.Player;

public interface Game {

    Integer getId();

    void addPlayer(Player player);

    void startGameRound() throws GameException;

    void sendAnswerToGameRound(Player player, Answer answer) throws GameException;

    GameRound getActiveGameRound();

    Result evaluateRound() throws GameException;

}
