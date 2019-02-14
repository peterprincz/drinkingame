package hu.kb.app.game.quiz;

import hu.kb.app.game.gamecycle.RareGameCycle;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String theQuestion;

    //@ElementCollection
    //private List<String> options;

    public Question() {
    }

    public Question(String theQuestion, List<String> options) {
        this.theQuestion = theQuestion;
        //this.options = options;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    //public List<String> getOptions() {
    //    return options;
    //}

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    //public void setOptions(List<String> options) {
    //    this.options = options;
    //}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
