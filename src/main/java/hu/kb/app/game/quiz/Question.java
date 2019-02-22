package hu.kb.app.game.quiz;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public @Data @NoArgsConstructor
class Question {


    private String question;

    private List<String> options;

    public Question(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return this.question.equals(question.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }
}
