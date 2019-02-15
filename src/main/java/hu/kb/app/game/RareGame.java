package hu.kb.app.game;

import hu.kb.app.game.gamecycle.GameCycle;
import hu.kb.app.game.gamecycle.RareGameCycle;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RareGame {

    private String name;

    private static Integer IntegerCounter = 0;

    private Integer id;

    private List<Player> players  = new ArrayList<>();

    private List<GameCycle> gameCycleList = new ArrayList<>();

    private GameCycle activeGameCycle;

    public RareGame() {
    }

    public void fillWithQuestions(List<Question> questions){
        questions.forEach(x-> gameCycleList.add(new RareGameCycle(x)));
    }

    public void startGameCycle(){
        activeGameCycle = gameCycleList.get(0);
        this.players.forEach(activeGameCycle::join);
        activeGameCycle.setStatus(Status.PREPARED);
        activeGameCycle.start();
    }


    public void sendAnswerToGameCycle(Player player, Answer answer){
        if( activeGameCycle == null || activeGameCycle.getStatus() != Status.ONGOING){
            throw new IllegalStateException("Game isn't going yet/is over yet :(");
        } else{
            activeGameCycle.handleAnswer(player, answer);
        }
    }

    public Result evaluteCycle(){
        Result result = activeGameCycle.evaluateResults();
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

    private static Integer getIntegerCounter() {
        return IntegerCounter;
    }

    private static void setIntegerCounter(Integer integerCounter) {
        IntegerCounter = integerCounter;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}