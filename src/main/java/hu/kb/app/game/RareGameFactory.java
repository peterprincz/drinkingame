package hu.kb.app.game;

import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.status.Status;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RareGameFactory {

    private static Integer IntegerCounter = 0;


    public static RareGame createRareGame(List<Question> questionList, String gameName){
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        rareGame.fillWithCycles(questionList);
        rareGame.setId(generateAndSetId());
        rareGame.setStatus(Status.CREATED);
        return rareGame;
    }

    public static RareGame createRareGameWithDefaultQuestions(String gameName){
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        rareGame.setId(generateAndSetId());
        rareGame.fillWithCycles(Arrays.asList(new Question("Ki lopott a Boltbol?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));
        rareGame.fillWithCycles(Arrays.asList(new Question("Kit vittek már be a rendörök?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));
        rareGame.fillWithCycles(Arrays.asList(new Question("Ki vágta le a saját haját?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));
        rareGame.fillWithCycles(Arrays.asList(new Question("Ki küldött már mást korházba?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));
        rareGame.fillWithCycles(Arrays.asList(new Question("Kit büntetett már meg kaller?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));
        rareGame.fillWithCycles(Arrays.asList(new Question("Ki sírt a suliban rossz jegyért?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));
        rareGame.fillWithCycles(Arrays.asList(new Question("Ki zárta már ki magát a saját lakásából?", new LinkedList<>(Arrays.asList("optionOne", "optionTwo")))));

        rareGame.setActiveGameCycle(rareGame.getGameCycleList().get(0));
        rareGame.setStatus(Status.CREATED);
        return rareGame;
    }

    private static Integer generateAndSetId(){
        IntegerCounter += 1;
        return IntegerCounter;
    }

}
