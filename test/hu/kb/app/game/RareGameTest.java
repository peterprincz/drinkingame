package hu.kb.app.game;

import hu.kb.app.game.gamecycle.RareGameCycle;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.status.Status;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        RareGameCycle rareGameCycle = (RareGameCycle)rareGame.fillWithQuestions(Collections.singletonList(question))
                                                             .getGameCycleList()
                                                             .get(0);
        assertEquals(question,rareGameCycle.getQuestion());
    }

    @org.junit.jupiter.api.Test
    void startGameCycle() {
        Question question = new Question("What is that?", Arrays.asList("option","option1","option2"));
        this.fillWithQuestions();
        rareGame.fillWithQuestions(Collections.singletonList(question));
        Question questionTest = rareGame.startGameCycle();
        assertEquals(question,questionTest);
    }

    @org.junit.jupiter.api.Test
    void sendAnswerToGameCycle() {
        Question question = new Question("What is that?", Arrays.asList("option","option1","option2"));
        this.fillWithQuestions();
        rareGame.fillWithQuestions(Collections.singletonList(question));
        rareGame.startGameCycle();
        RareGameCycle rareGameCycle = (RareGameCycle)rareGame.getActiveGameCycle();
        List<Answer> answers = rareGameCycle.getAnswers();
        Answer answer = new Answer(1,1,"test");
        rareGame.sendAnswerToGameCycle(answer);
        assertTrue(answers.contains(answer));
    }

    @Test
    void testNotOngoingGameAnswer(){
        assertThrows(IllegalStateException.class,()->rareGame.sendAnswerToGameCycle(new Answer(1,1,"test")));
    }

    @org.junit.jupiter.api.Test
    void evaluteCycle() {
    }

    @org.junit.jupiter.api.Test
    void addPlayer() {
    }
}