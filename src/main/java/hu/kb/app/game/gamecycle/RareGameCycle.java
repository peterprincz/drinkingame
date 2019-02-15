package hu.kb.app.game.gamecycle;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import java.util.*;

public class RareGameCycle implements GameCycle {

    private Integer id;

    private List<Player> players = new ArrayList<>();
    private Status status;

    private Question question;

    private Map<Player, Answer> answers = new HashMap<>();

    public RareGameCycle() {
    }

    public RareGameCycle(Question question) {
        this.status = Status.CREATED;
        this.question = question;
    }

    @Override
    public void join(Player player) {
        players.add(player);
    }

    @Override
    public Question start() {
        this.status = Status.ONGOING;
        return this.question;
    }

    @Override
    public void handleAnswer(Player player, Answer answer) {
        answers.put(player, answer);
    }

    @Override
    public Result evaluateResults() {
        this.status = Status.ENDED;
        Result result = new Result();
        Map<String,Integer> answerCounts = new HashMap<>();

        //Making a map of guesses
        for (Map.Entry<Player, Answer> entry: answers.entrySet()){
            String answer = entry.getValue().getAnswer();
            answerCounts.merge(answer, 1, (a, b) -> a + b);
        }
        Integer maximumguess = Collections.max(answerCounts.values());
        List<String> listOfPlayerWithMaximumGuesses = new ArrayList<>();
        //Making a list of players with the maximum guess
        for (Map.Entry<Player, Answer> entry : answers.entrySet()){
            if(entry.getValue().getAnswer().equals(maximumguess.toString())){
                listOfPlayerWithMaximumGuesses.add(entry.getKey().getName());
            }
        }//Selecting who managed to guess a people with the maximum amount of guess
        for (Map.Entry<Player, Answer> entry: answers.entrySet()){
            if(listOfPlayerWithMaximumGuesses.contains(entry.getValue().getAnswer())){
                result.getWinners().add(entry.getKey());
            } else {
                result.getLosers().add(entry.getKey());
            }
        }
        return result;
    }

    public Map<Player, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Player, Answer> answers) {
        this.answers = answers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
