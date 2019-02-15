package hu.kb.app.api;

import hu.kb.app.game.quiz.Answer;

public class SendAnswerRequest {

    private Integer playerId;

    private Answer answer;


    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
