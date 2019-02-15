package hu.kb.app.game;

import hu.kb.app.game.gamecycle.RareGameCycle;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.game.status.Status;
import hu.kb.app.player.Player;
import hu.kb.app.player.drinksetting.DrinkType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RareGameTest {

    private RareGame rareGame;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        rareGame = new RareGame();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        rareGame = null;
    }

    @org.junit.jupiter.api.Test
    void fillWithQuestions() {
        Question question = new Question("What is that?", Arrays.asList("option","option1","option2"));
        rareGame.fillWithQuestions(Collections.singletonList(question));
        RareGameCycle rareGameCycle = (RareGameCycle)rareGame.getGameCycleList().get(0);
        assertEquals(question,rareGameCycle.getQuestion());
    }

    @org.junit.jupiter.api.Test
    void startGameCycle() {
        Question question = new Question("What is that?", Arrays.asList("option","option1","option2"));
        this.fillWithQuestions();
        rareGame.fillWithQuestions(Collections.singletonList(question));
        rareGame.startGameCycle();
        assertEquals(Status.ONGOING,rareGame.getGameCycleList().get(0).getStatus());
    }

    @org.junit.jupiter.api.Test
    void sendAnswerToGameCycle() {
        Question question = new Question("What is that?", Arrays.asList("option","option1","option2"));
        this.fillWithQuestions();
        rareGame.fillWithQuestions(Collections.singletonList(question));
        rareGame.startGameCycle();
        RareGameCycle rareGameCycle = (RareGameCycle)rareGame.getActiveGameCycle();
        Answer answer = new Answer(1,"answer");
        Player player = new Player("karcsi",80, DrinkType.BEER);
        rareGame.sendAnswerToGameCycle(player,answer);
        assertTrue(rareGameCycle.getAnswers().containsKey(player));
    }

    @Test
    void testNotOngoingGameAnswer(){
        Player player = new Player("karcsi",80, DrinkType.BEER);
        Answer answer = new Answer(1,"answer");
        assertThrows(IllegalStateException.class,()->rareGame.sendAnswerToGameCycle(player,answer));
    }

    @org.junit.jupiter.api.Test
    void evaluteCycle() {
        rareGame.generateAndSetId();
        Player player1 = new Player("karcsi",80, DrinkType.BEER);
        Player player2 = new Player("pista",80, DrinkType.BEER);
        Player player3 = new Player("béla",80, DrinkType.BEER);
        Answer answer = new Answer(0,"josko");
        rareGame.fillWithQuestions(Collections.singletonList(new Question("question", Arrays.asList("option", "option1"))));
        rareGame.addPlayer(player1);
        rareGame.addPlayer(player2);
        rareGame.addPlayer(player3);
        rareGame.startGameCycle();
        rareGame.sendAnswerToGameCycle(player1,answer);
        rareGame.sendAnswerToGameCycle(player2,answer);
        rareGame.sendAnswerToGameCycle(player3,answer);
        Result result = rareGame.evaluteCycle();
        System.out.println(result.getWinners());
        System.out.println(result.getLosers());
        System.out.println("result:" + result.getResult());
        assertTrue(result.getWinners().contains(player1));
    }

    @org.junit.jupiter.api.Test
    void addPlayer() {
        Player player1 = new Player("karcsi",80, DrinkType.BEER);
        rareGame.addPlayer(player1);
        assertTrue(rareGame.getPlayers().contains(player1));
    }
}