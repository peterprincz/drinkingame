package hu.kb.app.api;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class JoinGameRequest {
    private Integer playerId;
    private Integer gameId;
}
