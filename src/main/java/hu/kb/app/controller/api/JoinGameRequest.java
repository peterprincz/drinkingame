package hu.kb.app.controller.api;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class JoinGameRequest {
    private Integer playerId;
    private Integer gameId;
}
