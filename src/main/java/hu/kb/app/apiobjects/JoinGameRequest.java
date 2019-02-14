package hu.kb.app.apiobjects;

import hu.kb.app.player.Player;

public class JoinGameRequest {
    private Integer playerId;
    private Integer id;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
