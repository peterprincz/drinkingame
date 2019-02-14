package hu.kb.app.controller;

import hu.kb.app.apiobjects.JoinGameRequest;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.RareGame;
import hu.kb.app.player.Player;
import hu.kb.app.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    GameService gameService;


    @RequestMapping(
            path = "create-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> createGame(){
        return ResponseEntity.ok(gameService.createGame());
    }

    @RequestMapping(
            path = "create-user",
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
        return ResponseEntity.ok(gameService.joinGame(joinGameRequest.getPlayerId(),joinGameRequest.getId()));
    }

    @RequestMapping(
            path = "start-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> startGame(@RequestBody Integer id){
        return ResponseEntity.ok(gameService.startGame(id));
    }

    @RequestMapping(
            path = "send-answer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> sendAnswerToGame(@RequestBody Answer answer){
        return ResponseEntity.ok(gameService.sendAnswerToGame(answer));
    }


    @RequestMapping(
            path = "end-game",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> endGame(@RequestBody Integer id){
        return ResponseEntity.ok(gameService.evaluateGameCycle(id));
    }



    //evaluateGameCycle


}
