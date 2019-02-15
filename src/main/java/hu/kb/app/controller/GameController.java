package hu.kb.app.controller;

import hu.kb.app.api.*;
import hu.kb.app.game.RareGame;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.player.Player;
import hu.kb.app.service.GameService;
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

    @Autowired
    private
    GameService gameService;


    @RequestMapping(
            path = "get-games",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<RareGame>> getGames(){
        return ResponseEntity.ok(gameService.getGames());
    }

    @RequestMapping(
            path = "create-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> createGame(@RequestBody CreateGameRequest createGameRequest){
        return ResponseEntity.ok(gameService.createGame(createGameRequest.getGameName()));
    }

    @RequestMapping(
            path = "create-player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> startGame(@RequestBody Player player){
        return ResponseEntity.ok(gameService.createPlayer(player));
    }


    @RequestMapping(
            path = "join-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> joinGame(@RequestBody JoinGameRequest joinGameRequest){
        simpMessagingTemplate.convertAndSend("/game/join","connected to the game");
        return ResponseEntity.ok(gameService.joinGame(joinGameRequest.getPlayerId(),joinGameRequest.getGameId()));
    }

    @RequestMapping(
            path = "start-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<RareGame> startGame(@RequestBody StartGameRequest startGameRequest){
        RareGame rareGame = gameService.startGameCycle(startGameRequest.getGameId());
        rareGame.startGameCycle();
        simpMessagingTemplate.convertAndSend("/game/start", rareGame.getActiveGameCycle().getQuestion());
        return ResponseEntity.ok(gameService.startGameCycle(startGameRequest.getGameId()));
    }

    @RequestMapping(
            path = "send-answer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> sendAnswerToGame(@RequestBody SendAnswerRequest sendAnswerRequest){
        return ResponseEntity.ok(gameService.sendAnswerToGame(sendAnswerRequest.getPlayerId(), sendAnswerRequest.getAnswer()));
    }

    @RequestMapping(
            path = "end-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result> endGame(@RequestBody EndGameRequest endGameRequest){
        simpMessagingTemplate.convertAndSend("/game/end", "the game has ended");
        return ResponseEntity.ok(gameService.evaluateGameCycle(endGameRequest.getGameId()));
    }

}
