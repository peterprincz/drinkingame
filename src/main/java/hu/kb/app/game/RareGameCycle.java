package hu.kb.app.game;

import hu.kb.app.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class RareGameCycle implements GameCycle {
    private List<Player> players = new ArrayList<>();
    private Status status;
    private Question question;
    private List<Answer> answers = new ArrayList<>();

    public RareGameCycle(Question question,List<Player> cyclePlayers) {
        players.addAll(cyclePlayers);
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
    public void handleAnswer(Answer answer) {
        answers.add(answer);
    }

    @Override
    public String evaluateResults() {
        this.status = Status.ENDED;
        Map<String,Integer> answerCounts = new HashMap<>();
        StringBuilder result = new StringBuilder();
        for (Answer answer:answers){
            String currentAns = answer.getAnswer();
            answerCounts.merge(currentAns, 1, (a, b) -> a + b);
        }
        for (String guess : answerCounts.keySet()){
            if(answerCounts.get(guess).equals(Collections.max(answerCounts.values()))){
                result.append(guess);
                result.append(",");
            }
        }

        return result.toString();

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
