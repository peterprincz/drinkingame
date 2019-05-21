package hu.kb.app.model.hurrygame;

import hu.kb.app.model.game.basegame.GameIdGenerator;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.game.basegame.Answer;
import hu.kb.app.model.game.basegame.Question;

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
            questionList.add(new Question("What was our biggest difficulity?",
                    Arrays.asList("Lack of motivation","Lack of knowledge","Coming up with a good idea","Not enough food"),
                    new Answer("Coming up with a good idea"))
            );
        questionList.add(new Question("Who is the other member of the team?",
                Arrays.asList("János Márton","Kurt Cobain","Péter Princz","Dániel Szendrei"),
                new Answer("Péter Princz"))
        );
        questionList.add(new Question("Which wifi you need to be connected to?",
                Arrays.asList("codecool-guest", "codecool-event","codecool-student","wifi from the neighbour"),
                new Answer("codecool-event"))
        );
        questionList.add(new Question("When should you drink?",
                Arrays.asList("If you answer the question wrong", "If you answer the question correctly","If you don't give any answer","If you go for a coffee break"),
                new Answer("If you answer the question correctly"))
        );


        questionList.forEach(hurryGame::addQuestion);

        hurryGame.setActiveGameRound(hurryGame.getGameRoundList().get(0));
        hurryGame.setStatus(Status.CREATED);
        return hurryGame;
    }

    private static Integer generateAndSetId(){
        IntegerCounter += 1;
        return IntegerCounter;
    }

}
