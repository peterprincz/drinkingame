package hu.kb.app.game.hurrygame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.IllegalGameStateException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.game.GameRound;
import hu.kb.app.game.enums.Status;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public @Data @NoArgsConstructor
class HurryGameGameRound implements GameRound {

    private Answer answer;
    private List<Player> players = new ArrayList<>();
    private Status status;
    private Question question;
    private Map<Player, HurryGameAnswer> submittedAnswers = new HashMap<>();
    private LocalDateTime roundStartTime;
    private final static Integer winnerLimit = 3;

    public HurryGameGameRound(Question question) {
        this.status = Status.CREATED;
        this.answer = question.getAnswer();
        this.question = question;
    }

    @Override
    public void join(Player player){
        this.players.add(player);
    }

    @Override
    public Question start(List<Player> players) throws GameException {
        players.forEach(this::join);
        this.roundStartTime = LocalDateTime.now();
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
        submittedAnswers.put(player, new HurryGameAnswer(answer));
    }

    @Override
    public Result evaluateResults() throws GameException {
        if(submittedAnswers.isEmpty()){
            throw new NoAnswersException("There isn't any answer in the round to evaluate");
        }
        Result result = new Result();
        result.setResult(this.answer.getAnswer());
        List<Map.Entry<Player,HurryGameAnswer>> sortedEntries =submittedAnswers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((Comparator.comparing(HurryGameAnswer::getSubmitTime))))
                .collect(Collectors.toList());


        for (int i = 0; i < sortedEntries.size(); i++) {
            if(i < HurryGameGameRound.winnerLimit){
                result.getWinners().add(sortedEntries.get(i).getKey());
            } else {
                result.getLosers().add(sortedEntries.get(i).getKey());
            }
        }
        return result;
    }

}
