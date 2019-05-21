package hu.kb.app.game.raregame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.IllegalGameStateException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.game.GameRound;
import hu.kb.app.game.model.*;
import hu.kb.app.game.enums.Status;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

public @Data @NoArgsConstructor
class RareGameRound extends BaseGameRound implements GameRound {

    private Map<Player, Answer> submittedAnswers = new HashMap<>();

    public RareGameRound(Question question) {
        super(question);
    }


    @Override
    public void handleAnswer(Player player, Answer answer) throws GameException {
        super.handleAnswer(player,answer);
        submittedAnswers.put(player, answer);
    }

    @Override
    public Result evaluateResults() throws NoAnswersException {
        Result result = new Result();
        Map<String,Integer> answerCounts = new HashMap<>();
        if(submittedAnswers.isEmpty()){
            this.status = Status.ENDED;
            throw new NoAnswersException("There isn't any answer in the round to evaluate");
        }
        //Making a map of guesses
        for (Map.Entry<Player, Answer> entry: submittedAnswers.entrySet()){
            String answer = entry.getValue().getAnswer();
            answerCounts.merge(answer, 1, Integer::sum);
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

        for (Map.Entry<Player, Answer> entry : submittedAnswers.entrySet()){
            String correctAnswer = entry.getValue().getAnswer();

            if(listOfPlayerWithMaximumGuesses.contains(correctAnswer)){
                result.addWinner(entry.getKey());
            } else {
                result.addLoser(entry.getKey());
            }
        }

        this.status = Status.ENDED;
        return result;
    }

}
