package hu.kb.app.model.game.basegame;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

public @Data @NoArgsConstructor
class Question {

    private String question;

    private List<String> options = new LinkedList<>();

    private Answer answer;

    public Question(String question, List<String> options, Answer answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public Question(String question) {
        this.question = question;
        this.options = new LinkedList<>();
        this.answer = new Answer();
    }

    public void addOption(String option){
        options.add(option);
    }
}
