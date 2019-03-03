package hu.kb.app.game.raregame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.IllegalGameStateException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.game.Round;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.game.Status;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

public @Data @NoArgsConstructor
class RareGameRound implements Round {

    private List<Player> players = new ArrayList<>();
    private Status status;
    private Question question;
    private Map<Player, Answer> submittedAnswers = new HashMap<>();


    public RareGameRound(Question question) {
        this.status = Status.CREATED;
        this.question = question;
    }

    @Override
    public void join(Player player){
        this.players.add(player);
    }

    @Override
    public Question start(List<Player> players) throws GameException {
        players.forEach(this::join);
        if(this.status != Status.CREATED){
            throw new IllegalGameStateException(status);
        }
        players.forEach(player -> {
            question.getOptions().add(player.getName());
        });
        this.status = Status.ONGOING;
        return this.question;
    }


    @Override
    public void handleAnswer(Player player, Answer answer) throws GameException {
       if(status != Status.ONGOING){
           throw new IllegalGameStateException(status);
       }
        submittedAnswers.put(player, answer);
    }

    @Override
    public Result evaluateResults() throws GameException {
        Result result = new Result();
        Map<String,Integer> answerCounts = new HashMap<>();
        if(submittedAnswers.isEmpty()){
            throw new NoAnswersException("There isn't any answer in the round to evaluate");
        }
        //Making a map of guesses
        for (Map.Entry<Player, Answer> entry: submittedAnswers.entrySet()){
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
        for (Map.Entry<Player, Answer> entry: submittedAnswers.entrySet()){
            if(listOfPlayerWithMaximumGuesses.contains(entry.getValue().getAnswer())){
                result.getWinners().add(entry.getKey());
            } else {
                result.getLosers().add(entry.getKey());
            }
        }
        this.status = Status.ENDED;
        return result;
    }

}
