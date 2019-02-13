package hu.kb.app.controller;

import hu.kb.app.game.Answer;
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

@RestController("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> createGame(@RequestBody Player host){
        return ResponseEntity.ok(gameService.createGame(host));
    }


    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> startGame(@RequestBody Player host){
        return ResponseEntity.ok(gameService.startGame(host));
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> sendAnswerToGame(@RequestBody Answer answer){
        return ResponseEntity.ok(gameService.sendAnswerToGame(answer));
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RareGame> endGame(@RequestBody Player host){
        return ResponseEntity.ok(gameService.createGame(host));
    }


}
