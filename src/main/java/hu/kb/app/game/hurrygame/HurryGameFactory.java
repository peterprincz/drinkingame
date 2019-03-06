package hu.kb.app.game.hurrygame;

import hu.kb.app.game.GameIdGenerator;
import hu.kb.app.game.enums.Status;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.model.Question;

import java.util.*;

public class HurryGameFactory {

    private static Integer IntegerCounter = 0;

    public static HurryGame createHurryGame(List<Question> questionList, String gameName){
        HurryGame hurryGame = new HurryGame();
        hurryGame.setName(gameName);
        questionList.forEach(hurryGame::addQuestion);
        hurryGame.setId(GameIdGenerator.generateGameId());
        hurryGame.setStatus(Status.CREATED);
        return hurryGame;
    }

    public static HurryGame createRareGameWithDefaultQuestions(String gameName){
        HurryGame hurryGame = new HurryGame();
        hurryGame.setName(gameName);
        hurryGame.setId(generateAndSetId());
        List<Question> questionList = new LinkedList<>();
            questionList.add(new Question("Ki lopott a Boltbol?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
            questionList.add(new Question("Kit vittek már be a rendörök?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
            questionList.add(new Question("Ki vágta le a saját haját?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
            questionList.add(new Question("Ki küldött már mást korházba?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
            questionList.add(new Question("Kit büntetett már meg kaller?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
            questionList.add(new Question("Ki sírt a suliban rossz jegyért?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
            questionList.add(new Question("Ki zárta már ki magát a saját lakásából?",
                    Arrays.asList("optionOne", "optionTwo"),
                    new Answer("answer"))
            );
        hurryGame.setActiveGameRound(hurryGame.getGameRoundList().get(0));
        hurryGame.setStatus(Status.CREATED);
        return hurryGame;
    }

    private static Integer generateAndSetId(){
        IntegerCounter += 1;
        return IntegerCounter;
    }

}
