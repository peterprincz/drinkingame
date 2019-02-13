package hu.kb.app.game;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;

    public Question(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
