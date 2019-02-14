package hu.kb.app.game.gamecycle;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;

import javax.persistence.*;
import java.util.*;

@Entity
public class RareGameCycle implements GameCycle {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ElementCollection
    private List<Player> players = new ArrayList<>();
    @Enumerated
    private Status status;

    private String question;

    @ElementCollection
    private List<Answer> answers = new ArrayList<>();

    public RareGameCycle() {
    }

    public RareGameCycle(String question) {
        this.status = Status.CREATED;
        this.question = question;
    }

    @Override
    public void join(Player player) {
        players.add(player);
    }

    @Override
    public String start() {
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
