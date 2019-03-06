package hu.kb.app.game.raregame;

import hu.kb.app.game.GameIdGenerator;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.enums.Status;

import java.util.Arrays;
import java.util.List;

public class RareGameFactory {


    public static RareGame createRareGame(List<Question> questionList, String gameName){
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        questionList.forEach(rareGame::addQuestion);
        rareGame.setId(GameIdGenerator.generateGameId());
        rareGame.setStatus(Status.CREATED);
        return rareGame;
    }

    public static RareGame createRareGameWithDefaultQuestions(String gameName){
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        rareGame.setId(GameIdGenerator.generateGameId());
        Arrays.asList(
            new Question("Ki lopott a Boltbol?"),
            new Question("Kit vittek már be a rendörök?"),
            new Question("Ki vágta le a saját haját?"),
            new Question("Ki küldött már mást korházba?"),
            new Question("Kit büntetett már meg kaller?"),
            new Question("Ki sírt a suliban rossz jegyért?"),
            new Question("Ki zárta már ki magát a saját lakásából?")
        ).forEach(rareGame::addQuestion);

        rareGame.setActiveGameRound(rareGame.getGameRoundList().get(0));
        rareGame.setStatus(Status.CREATED);
        return rareGame;
    }


}
