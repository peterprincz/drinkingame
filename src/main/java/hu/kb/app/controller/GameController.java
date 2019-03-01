package hu.kb.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.kb.app.api.*;
import hu.kb.app.exceptions.GameException;
import hu.kb.app.game.RareGame;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.player.Gender;
import hu.kb.app.player.Player;
import hu.kb.app.player.drinksetting.SipType;
import hu.kb.app.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private
    GameService gameService;

    @RequestMapping(
            path = "get-games",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<RareGame>> getGames() throws GameException {
        logger.info("Request to get all games");
        return ResponseEntity.ok(gameService.getGames());
    }

    @RequestMapping(
            path = "get-game/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<RareGame> getGame(@PathVariable Integer id) throws GameException {
        logger.info("Request to get game with id of " + id);
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @RequestMapping(
            path = "create-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> createGame(@RequestBody CreateGameRequest createGameRequest){

        logger.info("Request to create a game with the name" + createGameRequest.getGameName());
        return ResponseEntity.ok(gameService.createGame(createGameRequest.getGameName(), createGameRequest.getQuestions()));
    }

    @RequestMapping(
            path = "create-player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //TODO REMOVE NULLCHECKS
    public ResponseEntity<Player> startGame(@RequestBody CreatePlayerRequest createPlayerRequest){
        logger.info("Request to create a player with the the following parameters" +createPlayerRequest.toString());
        Player player =  gameService.createPlayer(
                createPlayerRequest.getName(),
                createPlayerRequest.getWeight(),
                createPlayerRequest.getDrinkType(),
                createPlayerRequest.getSipType() == null ? SipType.MEDIUM : createPlayerRequest.getSipType(),
                createPlayerRequest.getGender()  == null ? Gender.MALE : createPlayerRequest.getGender()
        );
        return ResponseEntity.ok(player);
    }


    @RequestMapping(
            path = "join-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> joinGame(@RequestBody JoinGameRequest joinGameRequest) throws GameException{
        logger.info("Request to join the the game " + joinGameRequest.getGameId() + " with the player" + joinGameRequest.getPlayerId());
        RareGame rareGame = gameService.joinGame(joinGameRequest.getPlayerId(), joinGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/join/" + joinGameRequest.getGameId(),"connected to the game");
        return ResponseEntity.ok(rareGame);
    }

    @RequestMapping(
            path = "start-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<RareGame> startGame(@RequestBody StartGameRequest startGameRequest) throws GameException {
        logger.info("request to start the game with the id of" + startGameRequest.getGameId());
        RareGame rareGame = gameService.startGameCycle(startGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/start", rareGame.getActiveGameRound().getQuestion());
        return ResponseEntity.ok(rareGame);
    }

    @RequestMapping(
            path = "send-answer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> sendAnswerToGame(@RequestBody SendAnswerRequest sendAnswerRequest) throws GameException {
        Integer playerId = sendAnswerRequest.getPlayerId();
        logger.info("request to send a answer to game "+ sendAnswerRequest.getAnswer().getGameId());
        return ResponseEntity.ok(gameService.sendAnswerToGame(playerId, sendAnswerRequest.getAnswer()));
    }

    @RequestMapping(
            path = "end-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result> endGame(@RequestBody EndGameRequest endGameRequest) throws GameException {
        logger.info("request to end a game with the id of "+ endGameRequest.getGameId());
        Result result = gameService.evaluateGameCycle(endGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/end", result);
        return ResponseEntity.ok(result);
    }


    @RequestMapping(
            path = "create-game-file",
            method = RequestMethod.POST)
    public ResponseEntity<RareGame> createGame(@RequestParam("gameName") String gameName, @RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Request to create a game with the name" + gameName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<Question> questions = mapper.readValue(new String(file.getBytes(), StandardCharsets.UTF_8).substring(1), new TypeReference<List<Question>>(){});
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName(gameName);
        createGameRequest.setQuestions(questions);
        return ResponseEntity.ok(gameService.createGame(createGameRequest.getGameName(), createGameRequest.getQuestions()));
    }
}
