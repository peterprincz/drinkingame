package hu.kb.app.game.quiz;

import hu.kb.app.game.gamecycle.RareGameCycle;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String theQuestion;


    public Question() {
    }

    public Question(String theQuestion, List<String> options) {
        this.theQuestion = theQuestion;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return theQuestion.equals(question.theQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theQuestion);
    }
}
