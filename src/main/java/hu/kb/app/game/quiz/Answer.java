package hu.kb.app.game.quiz;


import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor
class Answer {

    private Integer gameId;
    private String answer;

    public Answer(Integer gameId, String answer) {
        this.gameId = gameId;
        this.answer = answer;
    }



}
