package hu.kb.app.game;

import hu.kb.app.game.gamecycle.GameCycle;
import hu.kb.app.game.gamecycle.RareGameCycle;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RareGame {

    static Integer IntegerCounter = 0;

    Integer id;

    private List<Player> players  = new ArrayList<>();

    List<GameCycle> gameCycleList = new ArrayList<>();

    GameCycle activeGameCycle;

    public RareGame() {
    }

    public RareGame fillWithQuestions(List<Question> questions){
        questions.forEach(x-> gameCycleList.add(new RareGameCycle(x)));
        return this;
    }

    public Question startGameCycle(){
        activeGameCycle = gameCycleList.get(0);
        this.players.forEach(activeGameCycle::join);
        activeGameCycle.setStatus(Status.PREPARED);
        return activeGameCycle.start();
    }


    public void sendAnswerToGameCycle(Answer answer){
        if( activeGameCycle == null || activeGameCycle.getStatus() != Status.ONGOING){
            throw new IllegalStateException("Game isn't going yet/is over yet :(");
        } else{
            activeGameCycle.handleAnswer(answer);
        }
    }

    public String evaluteCycle(){
        String result = activeGameCycle.evaluateResults();
        this.gameCycleList.remove(0);
        this.activeGameCycle = null;
        return result;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public static Integer getIntegerCounter() {
        return IntegerCounter;
    }

    public static void setIntegerCounter(Integer integerCounter) {
        IntegerCounter = integerCounter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<GameCycle> getGameCycleList() {
        return gameCycleList;
    }

    public void setGameCycleList(List<GameCycle> gameCycleList) {
        this.gameCycleList = gameCycleList;
    }

    public GameCycle getActiveGameCycle() {
        return activeGameCycle;
    }

    public void setActiveGameCycle(GameCycle activeGameCycle) {
        this.activeGameCycle = activeGameCycle;
    }

    public void generateAndSetId(){
        setId(RareGame.getIntegerCounter());
        RareGame.setIntegerCounter(RareGame.getIntegerCounter() + 1);
    }
}
