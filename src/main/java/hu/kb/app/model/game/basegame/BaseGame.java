package hu.kb.app.model.game.basegame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class BaseGame {

    protected List<GameRound> gameRoundList = new ArrayList<>();

    protected GameRound activeGameRound;

    protected String name;

    protected Integer id;

    protected List<Player> players  = new ArrayList<>();

    protected Status status;

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public Result evaluateRound() throws GameException {
        Result result = null;
        try {
            result = activeGameRound.evaluateResults();
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
        activeGameRound.start();
    }

    public void sendAnswerToGameRound(Player player, Answer answer) throws GameException{
        activeGameRound.handleAnswer(player, answer);
    }


}
