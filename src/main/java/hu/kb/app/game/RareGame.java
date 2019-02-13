package hu.kb.app.game;

import hu.kb.app.player.Player;
import org.mockito.internal.util.collections.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RareGame {

    private int rounds;
    private List<RareGameCycle> gameCycles = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    public RareGame(int rounds) {
        this.rounds = rounds;
        this.gameCycles.add(new RareGameCycle(new Question("What the fuck am i doing here?",Arrays.asList("i dont know","no idea","ask someone else")),this.players));
    }

    public Question startGameCycle(){
        RareGameCycle gameCycle = gameCycles.get(0);
        if(gameCycle.getStatus() == Status.PREPARED){
            return gameCycle.start();
        }
        throw new IllegalStateException("Game has already been started/not prepared :(");
    }

    public void lockGame(){
        this.gameCycles.get(0).setStatus(Status.PREPARED);
    }

    public void sendAnswerToGameCycle(Answer answer){
        if(gameCycles.get(0).getStatus() == Status.ONGOING){
            gameCycles.get(0).handleAnswer(answer);
        } else{
            throw new IllegalStateException("Game isn't going yet/is over yet :(");
        }
    }

    public String getWinner(){
        return gameCycles.get(0).evaluateResults();
    }

    public void addPlayer(Player player){
        players.add(player);
    }

}
