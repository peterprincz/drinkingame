package hu.kb.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.kb.app.api.*;
import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.PlayerNotFoundException;
import hu.kb.app.game.Game;
import hu.kb.app.game.enums.GameType;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Player;
import hu.kb.app.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private GameService gameService;

    @GetMapping("/get-games")
    public List<Game> getGames(){
        logger.info("Request to get all games");
        return gameService.getGames();
    }

    @GetMapping("/get-game/{id}")
    public Game getGame(@PathVariable Integer id) throws GameException {
        logger.info("Request to get game with id of " + id);
        return gameService.getGameById(id);
    }

    @PostMapping("/create-game")
    public Game createGame(@RequestBody CreateGameRequest createGameRequest){
        logger.info("Request to create a game with the name" + createGameRequest.getGameName());
        Game game = gameService.createGame(createGameRequest.getGameName(), createGameRequest.getQuestions(), createGameRequest.getGameType());
        simpMessagingTemplate.convertAndSend("/game/created",game);
        return game;
    }

    @PostMapping("/create-player")
    public Player createPlayer(@RequestBody CreatePlayerRequest createPlayerRequest){
        logger.info("Request to create a player with the the following parameters" +createPlayerRequest.toString());
        Player player =  gameService.createPlayer(
                createPlayerRequest.getName(),
                createPlayerRequest.getWeight(),
                createPlayerRequest.getDrinkType(),
                createPlayerRequest.getSipType(),
                createPlayerRequest.getGender()
        );
        return player;
    }

    @PostMapping("/edit-player")
    public Player editPlayer(@RequestBody EditPlayerRequest editPlayerRequest) throws GameException{
        logger.info("Request to edit player with the the id of: " + editPlayerRequest.getPlayedId());
        return gameService.editPlayer(editPlayerRequest.getPlayedId(), editPlayerRequest.getDrinkType(), editPlayerRequest.getSipType());
    }

    @PostMapping("/join-game")
    public Game joinGame(@RequestBody JoinGameRequest joinGameRequest) throws GameException{
        logger.info("Request to join the the game " + joinGameRequest.getGameId() + " with the player" + joinGameRequest.getPlayerId());
        Game game = gameService.joinGame(joinGameRequest.getPlayerId(), joinGameRequest.getGameId());
        simpMessagingTemplate.convertAndSend("/game/join/" + joinGameRequest.getGameId(),"connected to the game");
        return game;
    }

    @PostMapping("/start-game")
    public Game startGame(@RequestBody StartGameRequest startGameRequest) throws GameException {
        Integer gameId = startGameRequest.getGameId();
        logger.info("request to start the game with the id of" + gameId);
        Game game = gameService.startGameRound(gameId);
        simpMessagingTemplate.convertAndSend("/game/start/" + gameId, game.getActiveGameRound().getQuestion());
        return game;
    }

    @PostMapping("/send-answer")
    public Game sendAnswerToGame(@RequestBody SendAnswerRequest sendAnswerRequest) throws GameException {
        logger.info("request to send a answer to game "+ sendAnswerRequest.getGameId());
        return gameService.sendAnswerToGame(sendAnswerRequest.getPlayerId(), sendAnswerRequest.getAnswer(), sendAnswerRequest.getGameId());
    }

    @PostMapping("/end-game")
    public Result endGame(@RequestBody EndGameRequest endGameRequest) throws GameException {
        Integer gameId = endGameRequest.getGameId();
        logger.info("request to end a game with the id of "+ gameId);
        Result result = gameService.evaluateGameRound(gameId);
        simpMessagingTemplate.convertAndSend("/game/end/" + gameId, result);
        return result;
    }


    @PostMapping("/create-game-file")
    public Game createGame(@RequestParam("gameName") String gameName, @RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Request to create a game with the name" + gameName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<Question> questions = mapper.readValue(new String(file.getBytes(), StandardCharsets.UTF_8).substring(1), new TypeReference<List<Question>>(){});
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName(gameName);
        createGameRequest.setQuestions(questions);
        createGameRequest.setGameType(GameType.HURRYGAME);
        return gameService.createGame(createGameRequest.getGameName(), createGameRequest.getQuestions(), createGameRequest.getGameType());
    }

    @GetMapping("/get-player/{id}")
    public Player getPlayer(@PathVariable Integer id) throws PlayerNotFoundException {
        logger.info("Request to get one player by id: " + id);
        return gameService.getPlayerBy(id);
    }

    @GetMapping("/get-players")
    public List<Player> getPlayers() {
        logger.info("Request to get all players");
        return gameService.getPlayers();
    }
}
