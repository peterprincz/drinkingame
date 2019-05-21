package hu.kb.app.model.raregame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.model.game.basegame.GameRound;
import hu.kb.app.model.game.basegame.Answer;
import hu.kb.app.model.game.basegame.BaseGameRound;
import hu.kb.app.model.game.basegame.Question;
import hu.kb.app.model.game.basegame.Result;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
public @Data @NoArgsConstructor
class RareGameRound extends BaseGameRound implements GameRound {

    public RareGameRound(Question question) {
        super(question);
    }

    private Map<Player, Answer> submittedAnswers = new HashMap<>();

    public Question start() throws GameException{
        players.forEach(player -> {
            question.addOption(player.getName());
        });
        return super.start();
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
