package hu.kb.app.game;

import hu.kb.app.api.exceptions.GameException;
import hu.kb.app.game.gamecycle.GameCycle;
import hu.kb.app.game.gamecycle.RareGameCycle;
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

    private List<RareGameCycle> gameCycleList = new ArrayList<>();

    private RareGameCycle activeGameCycle;

    private Status status;

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void fillWithCycles(List<Question> questions){
        questions.forEach(question-> gameCycleList.add(new RareGameCycle(question)));
    }

    public void startGameCycle() throws GameException{
        if(activeGameCycle == null){
            setStatus(Status.ONGOING);
            activeGameCycle = gameCycleList.get(0);
        }
        activeGameCycle.start(this.players);
    }

    public void sendAnswerToGameCycle(Player player, Answer answer) throws GameException{
        activeGameCycle.handleAnswer(player, answer);
    }

    public Result evaluteCycle() throws GameException {
        Result result = activeGameCycle.evaluateResults();
        this.gameCycleList.remove(0);
        if(gameCycleList.size() > 0){
            this.activeGameCycle = this.gameCycleList.get(0);
        } else {
            setStatus(Status.ENDED);
            result.setLastQuestion(true);
        }
        return result;
    }

}
