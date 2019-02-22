package hu.kb.app.api;

import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public @Data @NoArgsConstructor class EvaluateResultsResponse {

    private String answer;

    private Map<Player, Boolean> results = new HashMap<>();

}
