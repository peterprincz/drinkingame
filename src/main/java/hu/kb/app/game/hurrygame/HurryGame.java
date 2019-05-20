package hu.kb.app.game.hurrygame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.game.Game;
import hu.kb.app.game.enums.Status;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.BaseGame;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor
class HurryGame extends BaseGame implements Game {

    private List<HurryGameGameRound> gameRoundList = new ArrayList<>();

    protected HurryGameGameRound activeGameRound;



    public void addQuestion(Question question){
        gameRoundList.add(new HurryGameGameRound(question));
    }

    public Result evaluateRound() throws GameException {
        Result result = null;
        try {
            result = activeGameRound.evaluateResults();
        //TODO WHAT SHOULD BE THE LOGIC HERE?
        } catch (NoAnswersException e){
            setStatus(Status.ENDED);
            throw e;
        } finally {
            this.gameRoundList.remove(0);
            if(gameRoundList.size() > 0){
                this.activeGameRound = this.gameRoundList.get(0);
            } else {
                setStatus(Status.ENDED);
                assert result != null;
                result.setLastQuestion(true);
            }
        }

        return result;
    }

    public void startGameRound() throws GameException {
        if(activeGameRound == null){
            setStatus(Status.ONGOING);
            activeGameRound = gameRoundList.get(0);
        }
        players.forEach(activeGameRound::join);
        activeGameRound.start(this.players);
    }

    public void sendAnswerToGameRound(Player player, Answer answer) throws GameException{
        activeGameRound.handleAnswer(player, answer);
    }

}
