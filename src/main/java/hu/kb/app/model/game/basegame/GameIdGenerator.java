package hu.kb.app.model.game.basegame;

public class GameIdGenerator {

    private static Integer idCounter = 0;

    public static Integer generateGameId(){
        idCounter += 1;
        return idCounter;
    }
}
