package hu.kb.app.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.LinkedList;
import java.util.List;

public @Data @NoArgsConstructor
@Entity
class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String question;

    @ElementCollection
    private List<String> options;

    @Embedded
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
}
