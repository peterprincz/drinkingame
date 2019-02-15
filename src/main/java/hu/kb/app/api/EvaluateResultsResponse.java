package hu.kb.app.api;

import hu.kb.app.player.Player;

import java.util.HashMap;
import java.util.Map;

class EvaluateResultsResponse {

    private String answer;

    private Map<Player, Boolean> results = new HashMap<>();

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Map<Player, Boolean> getResults() {
        return results;
    }

    public void setResults(Map<Player, Boolean> results) {
        this.results = results;
    }
}
