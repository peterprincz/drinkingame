package hu.kb.app.game.raregame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.game.Game;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.game.enums.Status;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor
class RareGame implements Game {

    private String name;

    private Integer id;

    private List<Player> players  = new ArrayList<>();

    private List<RareGameRound> gameRoundList = new ArrayList<>();

    private RareGameRound activeGameRound;

    private Status status;

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void addQuestion(Question question){
        gameRoundList.add(new RareGameRound(question));
    }

    public void startGameRound() throws GameException{
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

    public Result evaluateRound() {
        Result result;
        try {
            result = activeGameRound.evaluateResults();
        //TODO WHAT SHOULD BE THE LOGIC HERE?
        } catch (NoAnswersException e){
            result = new Result();
            result.setResult("NO ANSWER WAS GIVEN");
        }
        this.gameRoundList.remove(0);
        if(gameRoundList.size() > 0){
            this.activeGameRound = this.gameRoundList.get(0);
        } else {
            setStatus(Status.ENDED);
            result.setLastQuestion(true);
        }
        return result;
    }

}
