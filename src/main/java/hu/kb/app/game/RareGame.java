package hu.kb.app.game;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.game.gameround.RareGameRound;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor
class RareGame {

    private String name;

    private Integer id;

    private List<Player> players  = new ArrayList<>();

    private List<RareGameRound> gameRoundList = new ArrayList<>();

    private RareGameRound activeGameRound;

    private Status status;

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void fillWithQuestions(List<Question> questions){
        questions.forEach(question-> gameRoundList.add(new RareGameRound(question)));
    }

    public void startGameRound() throws GameException{
        if(activeGameRound == null){
            setStatus(Status.ONGOING);
            activeGameRound = gameRoundList.get(0);
        }
        activeGameRound.start(this.players);
    }

    public void sendAnswerToGameRound(Player player, Answer answer) throws GameException{
        activeGameRound.handleAnswer(player, answer);
    }

    public Result evaluateRound() throws GameException {
        Result result = activeGameRound.evaluateResults();
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
