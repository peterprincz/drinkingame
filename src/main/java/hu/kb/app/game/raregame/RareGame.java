package hu.kb.app.game.raregame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.game.Game;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.BaseGame;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.game.enums.Status;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor
class RareGame extends BaseGame implements Game {

    public void addQuestion(Question question){
        gameRoundList.add(new RareGameRound(question));
    }

    public void startGameRound() throws GameException{
        if(activeGameRound == null){
            setStatus(Status.ONGOING);
            activeGameRound = gameRoundList.get(0);
        }
        players.forEach(activeGameRound::join);
        activeGameRound.start();
    }

    public void sendAnswerToGameRound(Player player, Answer answer) throws GameException{
        activeGameRound.handleAnswer(player, answer);
    }

}
