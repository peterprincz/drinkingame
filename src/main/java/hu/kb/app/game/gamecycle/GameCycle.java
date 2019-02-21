package hu.kb.app.game.gamecycle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import hu.kb.app.api.exceptions.GameException;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameCycle {

    protected Integer id;
    protected List<Player> players = new ArrayList<>();
    protected Status status;
    protected Question question;
    @JsonIgnore
    protected Map<Player, Answer> answers = new HashMap<>();


    public abstract void join(Player player) throws GameException;
    public abstract Question start(List<Player> players) throws GameException;
    public abstract void handleAnswer(Player player, Answer answer) throws GameException;
    public abstract Result evaluateResults() throws GameException;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Map<Player, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Player, Answer> answers) {
        this.answers = answers;
    }
}
