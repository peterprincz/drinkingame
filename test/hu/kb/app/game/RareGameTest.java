package hu.kb.app.model.game;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.model.game.basegame.*;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.raregame.RareGame;
import hu.kb.app.model.raregame.RareGameFactory;
import hu.kb.app.model.raregame.RareGameRound;
import hu.kb.app.model.player.Player;
import hu.kb.app.model.player.drinksetting.DrinkType;
import org.junit.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RareGameTest {

    private Game rareGame;

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
        Question questionOne = new Question("What is that?");
        Question questionTwo = new Question("What is this");
        ((RareGame) rareGame).addQuestion(questionOne);
        ((RareGame) rareGame).addQuestion(questionTwo);
        GameRound rareGameRoundOne = ((RareGame) rareGame).getGameRoundList().get(0);
        GameRound rareGameRoundTwo = ((RareGame) rareGame).getGameRoundList().get(1);
        assertEquals(questionOne,(rareGameRoundOne).getQuestion());
        assertEquals(questionTwo,(rareGameRoundTwo).getQuestion());
    }

    @org.junit.jupiter.api.Test
    void startGameCycle() throws GameException {
        Question question = new Question("What is that?");
        this.fillWithQuestions();
        ((RareGame) rareGame).addQuestion(question);
        rareGame.startGameRound();
        assertEquals(Status.ONGOING,rareGame.getActiveGameRound().getStatus());
    }

    @org.junit.jupiter.api.Test
    void sendAnswerToGameCycle() throws GameException {
        Question question = new Question("What is that?");
        this.fillWithQuestions();
        ((RareGame) rareGame).addQuestion(question);
        rareGame.startGameRound();
        RareGameRound rareGameRound = (RareGameRound) rareGame.getActiveGameRound();
        Answer answer = new Answer("answer");
        Player player = new Player("karcsi",80, DrinkType.BEER);
        rareGame.sendAnswerToGameRound(player,answer);
        assertTrue(rareGameRound.getSubmittedAnswers().containsKey(player));
    }

    @Test
    void testNotOngoingGameAnswer(){
        Player player = new Player("karcsi",80, DrinkType.BEER);
        Answer answer = new Answer("answer");
        assertThrows(IllegalStateException.class,()->rareGame.sendAnswerToGameRound(player,answer));
    }

    @org.junit.jupiter.api.Test
    void evaluteCycle() throws GameException {
        Player player1 = new Player("karcsi",80, DrinkType.BEER);
        Player player2 = new Player("pista",80, DrinkType.BEER);
        Player player3 = new Player("béla",80, DrinkType.BEER);
        Answer answer = new Answer("josko");
        rareGame = RareGameFactory.createRareGame((Collections.singletonList(new Question("question"))), "gameOne");
        rareGame.addPlayer(player1);
        rareGame.addPlayer(player2);
        rareGame.addPlayer(player3);
        rareGame.startGameRound();
        rareGame.sendAnswerToGameRound(player1,answer);
        rareGame.sendAnswerToGameRound(player2,answer);
        rareGame.sendAnswerToGameRound(player3,answer);
        Result result = rareGame.evaluateRound();
        assertTrue(result.getWinners().contains(player1));
        assertTrue(result.isLastQuestion());
    }

    @org.junit.jupiter.api.Test
    void addPlayer() {
        Player player1 = new Player("karcsi",80, DrinkType.BEER);
        rareGame.addPlayer(player1);
        assertTrue(((RareGame) rareGame).getPlayers().contains(player1));
    }
}