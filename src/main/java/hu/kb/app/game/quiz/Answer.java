package hu.kb.app.game.quiz;

import hu.kb.app.player.Player;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gameId;
    private Integer playerId;
    private String answer;

    public Answer(){}

    public Answer(Integer gameId, Integer playerId, String answer) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.answer = answer;
    }


    public Integer getPlayerId() {
        return playerId;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
