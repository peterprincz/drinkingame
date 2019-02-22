package hu.kb.app.game;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.kb.app.api.*;
import hu.kb.app.controller.GameController;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.player.Player;
import hu.kb.app.player.drinksetting.DrinkType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    GameController gameController;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.gameController).build();
    }


    @Test
    public void TestAGame() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String gameName = "gameOne";
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName(gameName);

        String CreateGameRequestJson=ow.writeValueAsString(createGameRequest);


        MvcResult createGameResult = mockMvc.perform(post("/create-game")
                .content(CreateGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.gameCycleList[0].question.question", is("QUESTION1")))
                .andExpect(jsonPath("$.gameCycleList[1].question.question", is("QUESTION2")))
                .andExpect(jsonPath("$.activeGameCycle.question.question", is("QUESTION1")))
                .andReturn();

        RareGame responseRareGame = mapper.readValue(createGameResult.getResponse().getContentAsString(), RareGame.class);

        Player player = new Player("playerOne", 20, DrinkType.WHISKEY);
        String playerJson = ow.writeValueAsString(player);
        MvcResult createPlayerResult = mockMvc.perform(post("/create-player")
                .content(playerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("playerOne")))
                .andReturn();

        Player responsePlayer = mapper.readValue(createPlayerResult.getResponse().getContentAsString(), Player.class);

        JoinGameRequest joinGameRequest = new JoinGameRequest();
        joinGameRequest.setGameId(responseRareGame.getId());
        joinGameRequest.setPlayerId(responsePlayer.getId());

        String joinGameRequestJson=ow.writeValueAsString(joinGameRequest);


        MvcResult joinGameResult = mockMvc.perform(post("/join-game")
                .content(joinGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players[0].name", is("playerOne")))
                .andReturn();

        responseRareGame = mapper.readValue(joinGameResult.getResponse().getContentAsString(), RareGame.class);
        assertTrue(responseRareGame.getPlayers().contains(responsePlayer));
        assertFalse(responseRareGame.getGameCycleList().isEmpty());

        StartGameRequest startGameRequest = new StartGameRequest();
        startGameRequest.setGameId(responseRareGame.getId());

        String startGameRequestJson = ow.writeValueAsString( startGameRequest);

        MvcResult startGameResult = mockMvc.perform(post("/start-game")
                .content(startGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeGameCycle.status", is("ONGOING")))
                .andReturn();


        SendAnswerRequest sendAnswerRequest = new SendAnswerRequest();
        sendAnswerRequest.setPlayerId(responsePlayer.getId());
        Answer answer = new Answer();
        answer.setGameId(responseRareGame.getId());
        answer.setAnswer("BÉLA");
        sendAnswerRequest.setAnswer(answer);
        startGameRequest.setGameId(responseRareGame.getId());

        String sendAnswerRequestJson = ow.writeValueAsString( sendAnswerRequest);

        MvcResult sendAnswerResult = mockMvc.perform(post("/send-answer")
                .content(sendAnswerRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();


        EndGameRequest endGameRequest = new EndGameRequest();
        endGameRequest.setGameId(responseRareGame.getId());

        String endGameRequestJson = ow.writeValueAsString(endGameRequest);

        MvcResult endGameResult = mockMvc.perform(post("/end-game")
                .content(endGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result",is("[BÉLA]")))
                .andExpect(jsonPath("$.lastQuestion", is(false)))
                .andExpect(jsonPath("$.winners", hasSize(1)))
                .andExpect(jsonPath("$.losers", hasSize(0)))
                .andReturn();


        MvcResult startGame2Result = mockMvc.perform(post("/start-game")
                .content(startGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeGameCycle.status", is("ONGOING")))
                .andReturn();



        MvcResult sendAnswer2Result = mockMvc.perform(post("/send-answer")
                .content(sendAnswerRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();


        MvcResult endGame2Result = mockMvc.perform(post("/end-game")
                .content(endGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result",is("[BÉLA]")))
                .andExpect(jsonPath("$.lastQuestion", is(false)))
                .andExpect(jsonPath("$.winners", hasSize(1)))
                //.andExpect(jsonPath("$.winners[0].drinkCount", is(2)))
                .andExpect(jsonPath("$.losers", hasSize(0)))
                .andReturn();


        MvcResult startGame3Result = mockMvc.perform(post("/start-game")
                .content(startGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeGameCycle.status", is("ONGOING")))
                .andReturn();



        MvcResult sendAnswer3Result = mockMvc.perform(post("/send-answer")
                .content(sendAnswerRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();


        MvcResult endGame3Result = mockMvc.perform(post("/end-game")
                .content(endGameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result",is("[BÉLA]")))
                .andExpect(jsonPath("$.lastQuestion", is(true)))
                .andExpect(jsonPath("$.winners", hasSize(1)))
                .andExpect(jsonPath("$.losers", hasSize(0)))
                .andReturn();
    }


}
