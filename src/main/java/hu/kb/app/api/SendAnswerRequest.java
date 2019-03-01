package hu.kb.app.api;

import hu.kb.app.game.quiz.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor
class SendAnswerRequest {

    private Integer playerId;
    private Answer answer;

}
