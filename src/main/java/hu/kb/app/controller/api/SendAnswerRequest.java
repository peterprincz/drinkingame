package hu.kb.app.controller.api;

import hu.kb.app.model.game.basegame.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor
class SendAnswerRequest {

    private Integer playerId;
    private Answer answer;
    private Integer gameId;

}
