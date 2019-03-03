package hu.kb.app.game.hurrygame;

import hu.kb.app.game.GameIdGenerator;
import hu.kb.app.game.Status;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Question;

import java.util.HashMap;
import java.util.Map;

public class HurryGameFactory {

    private static Integer IntegerCounter = 0;

    public static HurryGame createHurryGame(Map<Question, Answer> questionMap, String gameName){
        HurryGame hurryGame = new HurryGame();
        hurryGame.setName(gameName);
        questionMap.forEach(hurryGame::addQuestion);
        hurryGame.setId(GameIdGenerator.generateGameId());
        hurryGame.setStatus(Status.CREATED);
        return hurryGame;
    }

    public static HurryGame createRareGameWithDefaultQuestions(String gameName){
        HurryGame hurryGame = new HurryGame();
        hurryGame.setName(gameName);
        hurryGame.setId(generateAndSetId());
        Map<Question, Answer> questionMap = new HashMap<>();
            questionMap.put(new Question("Ki lopott a Boltbol?"), new Answer("answer"));
            questionMap.put(new Question("Kit vittek már be a rendörök?"), new Answer("answer"));
            questionMap.put(new Question("Ki vágta le a saját haját?"), new Answer("answer"));
            questionMap.put(new Question("Ki küldött már mást korházba?"), new Answer("answer"));
            questionMap.put(new Question("Kit büntetett már meg kaller?"), new Answer("answer"));
            questionMap.put(new Question("Ki sírt a suliban rossz jegyért?"), new Answer("answer"));
            questionMap.put(new Question("Ki zárta már ki magát a saját lakásából?"), new Answer("answer"));
        hurryGame.setActiveGameRound(hurryGame.getGameRoundList().get(0));
        hurryGame.setStatus(Status.CREATED);
        return hurryGame;
    }

    private static Integer generateAndSetId(){
        IntegerCounter += 1;
        return IntegerCounter;
    }

}
