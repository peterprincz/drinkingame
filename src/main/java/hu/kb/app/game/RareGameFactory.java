package hu.kb.app.game;

import hu.kb.app.game.quiz.Question;

import java.util.Arrays;
import java.util.List;

public class RareGameFactory {


    public static RareGame createRareGame(List<Question> questionList, String gameName){
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        rareGame.generateAndSetId();
        rareGame.fillWithCycles(questionList);
        return rareGame;
    }

    public static RareGame createRareGameWithDefaultQuestions(String gameName){
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        rareGame.generateAndSetId();
        rareGame.fillWithCycles(Arrays.asList(new Question("QUESTION1", Arrays.asList("optionOne", "optionTwo"))));
        rareGame.fillWithCycles(Arrays.asList(new Question("QUESTION2", Arrays.asList("optionOne", "optionTwo"))));
        rareGame.fillWithCycles(Arrays.asList(new Question("QUESTION3", Arrays.asList("optionOne", "optionTwo"))));
        rareGame.setActiveGameCycle(rareGame.getGameCycleList().get(0));
        return rareGame;
    }

}
