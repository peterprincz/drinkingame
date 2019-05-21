package hu.kb.app.model.hurrygame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.model.game.basegame.GameRound;
import hu.kb.app.model.game.basegame.Answer;
import hu.kb.app.model.game.basegame.BaseGameRound;
import hu.kb.app.model.game.basegame.Question;
import hu.kb.app.model.game.basegame.Result;
import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public @Data @NoArgsConstructor
class HurryGameGameRound extends BaseGameRound implements GameRound {

    public HurryGameGameRound(Question question) {
        super(question);
    }

    private Map<Player, HurryGameAnswer> submittedAnswers = new HashMap<>();
    private LocalDateTime roundStartTime;
    private final static Integer winnerLimit = 3;

    public Question start() throws GameException {
        this.roundStartTime = LocalDateTime.now();
        return super.start();
    }


    @Override
    public void handleAnswer(Player player, Answer answer) throws GameException {
       super.handleAnswer(player,answer);
        submittedAnswers.put(player, new HurryGameAnswer(answer.getAnswer()));
    }

    @Override
    public Result evaluateResults() throws GameException {
        if(submittedAnswers.isEmpty()){
            throw new NoAnswersException("There isn't any answer in the round to evaluate");
        }
        Result result = new Result(this.question.getAnswer().getAnswer());

        List<Map.Entry<Player,HurryGameAnswer>> sortedEntries = submittedAnswers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((Comparator.comparing(HurryGameAnswer::getSubmitTime))))
                .collect(Collectors.toList());

        int counter = 0;

        for (Map.Entry<Player, HurryGameAnswer> sortedEntry : sortedEntries) {
            String correctAnswer = result.getResult();
            String givenAnswer = sortedEntry.getValue().getAnswer();

            if (givenAnswer.equals(correctAnswer) && counter < winnerLimit) {
                counter++;
                result.addWinner(sortedEntry.getKey());
            } else {
                result.addLoser(sortedEntry.getKey());
            }
        }
        return result;
    }

}
