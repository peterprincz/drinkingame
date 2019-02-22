package hu.kb.app.game;

import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.status.Status;

import java.util.Arrays;
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
        rareGame.fillWithCycles(Arrays.asList(new Question("QUESTION1", Arrays.asList("optionOne", "optionTwo"))));
        rareGame.fillWithCycles(Arrays.asList(new Question("QUESTION2", Arrays.asList("optionOne", "optionTwo"))));
        rareGame.fillWithCycles(Arrays.asList(new Question("QUESTION3", Arrays.asList("optionOne", "optionTwo"))));
        rareGame.setActiveGameCycle(rareGame.getGameCycleList().get(0));
        rareGame.setStatus(Status.CREATED);
        return rareGame;
    }

    private static Integer generateAndSetId(){
        IntegerCounter += 1;
        return IntegerCounter;
    }

}
