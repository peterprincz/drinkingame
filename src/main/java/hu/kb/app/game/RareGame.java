package hu.kb.app.game;

import hu.kb.app.game.gamecycle.RareGameCycle;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class RareGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ElementCollection(targetClass = Player.class)
    private List<Player> players = new ArrayList<>();

    public RareGame() {
        this.id = 0;
    }


    public Question startGameCycle(List<RareGameCycle> gameCycles){
        RareGameCycle gameCycle = gameCycles.get(0);
        this.players.forEach(gameCycle::join);
        gameCycle.setStatus(Status.PREPARED);
        Question question = gameCycle.start();
        return question;
    }


    public void sendAnswerToGameCycle(Answer answer,List<RareGameCycle> gameCycles){
        if(gameCycles.get(0).getStatus() == Status.ONGOING){
            gameCycles.get(0).handleAnswer(answer);
        } else{
            throw new IllegalStateException("Game isn't going yet/is over yet :(");
        }
    }

    public String evaluteCycle(List<RareGameCycle> gameCycles){
        return gameCycles.get(0).evaluateResults();
    }

    public void addPlayer(Player player){
        players.add(player);
    }


    public Integer getId() {
        return id;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
