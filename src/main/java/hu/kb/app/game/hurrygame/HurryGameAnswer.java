package hu.kb.app.game.hurrygame;

import hu.kb.app.game.model.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public @Data @NoArgsConstructor
class HurryGameAnswer {

    String answer;
    LocalDateTime submitTime;

    public HurryGameAnswer(Answer answer){
        this.answer = answer.getAnswer();
        this.submitTime = LocalDateTime.now();
    }

}
