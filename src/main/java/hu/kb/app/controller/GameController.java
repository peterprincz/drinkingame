package hu.kb.app.controller;

import hu.kb.app.api.*;
import hu.kb.app.api.exceptions.GameException;
import hu.kb.app.game.RareGame;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.player.Player;
import hu.kb.app.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class GameController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    Logger logger = LoggerFactory.getLogger(GameController.class);


    @Autowired
    private
    GameService gameService;


    @RequestMapping(
            path = "get-games",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<RareGame>> getGames() throws GameException {
        logger.debug("Request to get all games");
        return ResponseEntity.ok(gameService.getGames());
    }

    @RequestMapping(
            path = "create-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> createGame(@RequestBody CreateGameRequest createGameRequest){
        logger.debug("Request to create a game with the name" + createGameRequest.getGameName());
        return ResponseEntity.ok(gameService.createGame(createGameRequest.getGameName()));
    }

    @RequestMapping(
            path = "create-player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> startGame(@RequestBody Player player){
        logger.debug("Request to create a player with the the following parameters" + player.toString());
        return ResponseEntity.ok(gameService.createPlayer(player));
    }


    @RequestMapping(
            path = "join-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> joinGame(@RequestBody JoinGameRequest joinGameRequest) throws GameException{
        logger.debug("Request to join the the game " + joinGameRequest.getGameId() + " with the player" + joinGameRequest.getPlayerId());
        simpMessagingTemplate.convertAndSend("/game/join/" + joinGameRequest.getGameId(),"connected to the game");
        return ResponseEntity.ok(gameService.joinGame(joinGameRequest.getPlayerId(),joinGameRequest.getGameId()));
    }

    @RequestMapping(
            path = "start-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<RareGame> startGame(@RequestBody StartGameRequest startGameRequest) throws GameException {
        logger.debug("request to start the game with the id of" + startGameRequest.getGameId());
        RareGame rareGame = gameService.startGameCycle(startGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/start", rareGame.getActiveGameCycle().getQuestion());
        return ResponseEntity.ok(rareGame);
    }

    @RequestMapping(
            path = "send-answer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> sendAnswerToGame(@RequestBody SendAnswerRequest sendAnswerRequest) throws GameException {
        logger.debug("request to send a answer to game "+ sendAnswerRequest.getAnswer().getGameId());
        return ResponseEntity.ok(gameService.sendAnswerToGame(sendAnswerRequest.getPlayerId(), sendAnswerRequest.getAnswer()));
    }

    @RequestMapping(
            path = "end-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result> endGame(@RequestBody EndGameRequest endGameRequest) throws GameException {
        logger.debug("request to end a game with the id of "+ endGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/end", "the game has ended");
        return ResponseEntity.ok(gameService.evaluateGameCycle(endGameRequest.getGameId()));
    }

}
