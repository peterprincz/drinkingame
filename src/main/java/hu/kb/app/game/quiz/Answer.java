package hu.kb.app.game.quiz;



public class Answer {

    private Integer gameId;
    private String answer;

    public Answer(){}

    public Answer(Integer gameId, String answer) {
        this.gameId = gameId;
        this.answer = answer;
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
