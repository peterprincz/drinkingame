package hu.kb.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.kb.app.api.*;
import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.PlayerNotFoundException;
import hu.kb.app.game.Game;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Player;
import hu.kb.app.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
    public ResponseEntity<List<Game>> getGames() throws GameException {
        logger.info("Request to get all games");
        return ResponseEntity.ok(gameService.getGames());
    }

    @RequestMapping(
            path = "get-game/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Game> getGame(@PathVariable Integer id) throws GameException {
        logger.info("Request to get game with id of " + id);
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @RequestMapping(
            path = "create-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> createGame(@RequestBody CreateGameRequest createGameRequest){

        logger.info("Request to create a game with the name" + createGameRequest.getGameName());
        return ResponseEntity.ok(gameService.createGame(createGameRequest.getGameName(), createGameRequest.getQuestions(), createGameRequest.getGameType()));
    }

    @RequestMapping(
            path = "create-player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerRequest createPlayerRequest){
        logger.info("Request to create a player with the the following parameters" +createPlayerRequest.toString());
        Player player =  gameService.createPlayer(
                createPlayerRequest.getName(),
                createPlayerRequest.getWeight(),
                createPlayerRequest.getDrinkType(),
                createPlayerRequest.getSipType(),
                createPlayerRequest.getGender()
        );
        return ResponseEntity.ok(player);
    }

    @RequestMapping(
            path = "edit-player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> editPlayer(@RequestBody EditPlayerRequest editPlayerRequest) throws GameException{
        logger.info("Request to edit player with the the id of: " + editPlayerRequest.getPlayedId());
        Player editedPlayer = gameService.editPlayer(editPlayerRequest.getPlayedId(), editPlayerRequest.getDrinkType(), editPlayerRequest.getSipType());
        return ResponseEntity.ok(editedPlayer);
    }

    @RequestMapping(
            path = "join-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> joinGame(@RequestBody JoinGameRequest joinGameRequest) throws GameException{
        logger.info("Request to join the the game " + joinGameRequest.getGameId() + " with the player" + joinGameRequest.getPlayerId());
        Game game = gameService.joinGame(joinGameRequest.getPlayerId(), joinGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/join/" + joinGameRequest.getGameId(),"connected to the game");
        return ResponseEntity.ok(game);
    }

    @RequestMapping(
            path = "start-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Game> startGame(@RequestBody StartGameRequest startGameRequest) throws GameException {
        logger.info("request to start the game with the id of" + startGameRequest.getGameId());
        Game game = gameService.startGameRound(startGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/start", game.getActiveGameRound().getQuestion());
        return ResponseEntity.ok(game);
    }

    @RequestMapping(
            path = "send-answer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> sendAnswerToGame(@RequestBody SendAnswerRequest sendAnswerRequest) throws GameException {
        logger.info("request to send a answer to game "+ sendAnswerRequest.getGameId());
        return ResponseEntity.ok(gameService.sendAnswerToGame(sendAnswerRequest.getPlayerId(), sendAnswerRequest.getAnswer(), sendAnswerRequest.getGameId()));
    }

    @RequestMapping(
            path = "end-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result> endGame(@RequestBody EndGameRequest endGameRequest) throws GameException {
        logger.info("request to end a game with the id of "+ endGameRequest.getGameId());
        Result result = gameService.evaluateGameRound(endGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/end", result);
        return ResponseEntity.ok(result);
    }


    @RequestMapping(
            path = "create-game-file",
            method = RequestMethod.POST)
    public ResponseEntity<Game> createGame(@RequestParam("gameName") String gameName, @RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Request to create a game with the name" + gameName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<Question> questions = mapper.readValue(new String(file.getBytes(), StandardCharsets.UTF_8).substring(1), new TypeReference<List<Question>>(){});
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName(gameName);
        createGameRequest.setQuestions(questions);
        return ResponseEntity.ok(gameService.createGame(createGameRequest.getGameName(), createGameRequest.getQuestions(), createGameRequest.getGameType()));
    }

    @RequestMapping(
            path = "get-player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> getPlayer(@RequestBody GetPlayerRequest getPlayerRequest) throws PlayerNotFoundException {
        logger.info("Request to get one player by id");
        return ResponseEntity.ok(gameService.getPlayerBy(getPlayerRequest.getPlayerId()));
    }

    @RequestMapping(
            path = "get-players",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<Player>> getPlayers() {
        logger.info("Request to get all players");
        return ResponseEntity.ok(gameService.getPlayers());
    }
}
