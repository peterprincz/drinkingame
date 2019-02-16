package hu.kb.app.game.gamecycle;

import hu.kb.app.api.exceptions.GameException;
import hu.kb.app.api.exceptions.IllegalGameStateException;
import hu.kb.app.api.exceptions.NoAnswersException;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import java.util.*;

public class RareGameCycle extends GameCycle {



    public RareGameCycle() {
    }

    public RareGameCycle(Question question) {
        this.status = Status.CREATED;
        this.question = question;
    }

    @Override
    public void join(Player player) throws GameException {
        if(status != Status.ONGOING){
            throw new IllegalGameStateException(status);
        }
        players.add(player);
    }

    @Override
    public Question start() throws GameException {
        if(this.status != Status.CREATED){
            throw new IllegalGameStateException(status);
        }
        this.status = Status.ONGOING;
        return this.question;
    }

    @Override
    public void handleAnswer(Player player, Answer answer) throws GameException {
       if(status != Status.ONGOING){
           throw new IllegalGameStateException(status);
       }
        answers.put(player, answer);
    }

    @Override
    public Result evaluateResults() throws GameException {
        this.status = Status.ENDED;
        Result result = new Result();
        Map<String,Integer> answerCounts = new HashMap<>();
        if(answers.isEmpty()){
            throw new NoAnswersException("There isn't any answer in the Cycle to evaluate");
        }
        //Making a map of guesses
        for (Map.Entry<Player, Answer> entry: answers.entrySet()){
            String answer = entry.getValue().getAnswer();
            answerCounts.merge(answer, 1, (a, b) -> a + b);
        }
        Integer maximumguess = Collections.max(answerCounts.values());
        List<String> listOfPlayerWithMaximumGuesses = new ArrayList<>();
        //Making a list of players with the maximum guess
        for (Map.Entry<String, Integer> entry : answerCounts.entrySet()){
            if(entry.getValue().equals(maximumguess)){
                listOfPlayerWithMaximumGuesses.add(entry.getKey());
            }
        }
        result.setResult(listOfPlayerWithMaximumGuesses.toString());
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
