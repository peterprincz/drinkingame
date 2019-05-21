package hu.kb.app.service;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.GameNotFoundException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.exceptions.PlayerNotFoundException;
import hu.kb.app.controller.GameController;
import hu.kb.app.game.Game;
import hu.kb.app.game.enums.GameType;
import hu.kb.app.game.hurrygame.HurryGame;
import hu.kb.app.game.hurrygame.HurryGameFactory;
import hu.kb.app.game.raregame.RareGameFactory;
import hu.kb.app.game.model.Answer;
import hu.kb.app.game.raregame.RareGame;
import hu.kb.app.game.model.Question;
import hu.kb.app.game.model.Result;
import hu.kb.app.player.Gender;
import hu.kb.app.player.Player;
import hu.kb.app.player.drinksetting.DrinkType;
import hu.kb.app.player.drinksetting.SipType;
import hu.kb.app.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    private
    PlayerRepository playerRepository;

    @Autowired
    private AlcoholCalculatorService alcoholCalculatorService;

    private Logger logger = LoggerFactory.getLogger(GameController.class);

    private List<Game> gameList = new ArrayList<>();

    public Game createGame(String gameName, List<Question> questions, GameType gameType){
        if(GameType.RAREGAME.equals(gameType)){
            RareGame rareGame;
            if(questions == null || questions.isEmpty()) {
                logger.info("Creating game with default questions:" + gameName);
                rareGame = RareGameFactory.createRareGameWithDefaultQuestions(gameName);
            } else {
                logger.info("Creating game with provided questions:" + gameName);
                rareGame = RareGameFactory.createRareGame(questions, gameName);
            }
            logger.info("Adding game to the gameList");
            this.gameList.add(rareGame);
            logger.info("Successfully added game: " + rareGame.getName());
            logger.info("Number of games: " + gameList.size());
            return rareGame;
        }
        if(GameType.HURRYGAME.equals(gameType)){
            HurryGame hurryGame;
            if(questions == null || questions.isEmpty()) {
                logger.info("Creating game with default questions:" + gameName);
                hurryGame = HurryGameFactory.createRareGameWithDefaultQuestions(gameName);
            } else {
                logger.info("Creating game with provided questions:" + gameName);
                hurryGame =HurryGameFactory.createHurryGame(questions, gameName);
            }
            logger.info("Adding game to the gameList");
            this.gameList.add(hurryGame);
            logger.info("Successfully added game: " + hurryGame.getName());
            logger.info("Number of games: " + gameList.size());
            return hurryGame;
        }
        throw new RuntimeException("INVALID GAMETYPE" + gameType);
    }

    public Player createPlayer(String name, Double weight, DrinkType drinkType, SipType sipType, Gender gender ){
        Player player = new Player(name, weight, drinkType, sipType, gender);
        logger.info("Saving player to database: " + player.getName());
        return playerRepository.save(player);
    }

    public Player getPlayerBy(Integer id) throws PlayerNotFoundException{
        return playerRepository.findById(id).orElseThrow(()->new PlayerNotFoundException("Player not found"));
    }

    public List<Player> getPlayers(){
        Iterable<Player> playerIterator = playerRepository.findAll();
        List<Player> players = new ArrayList<>();
        playerIterator.forEach(players::add);
        return players;
    }

    public Game joinGame(Integer playerId,Integer gameId) throws GameException{
        Game game = gameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new GameNotFoundException(gameId));
        logger.info("Player:{" + playerId +"} joining game: " + gameId);
        game.addPlayer(playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId)));
        logger.info("Player:{" + playerId +"} successfully joined game: " + gameId);
        return game;
    }

    public Game startGameRound(Integer gameId) throws GameException {
        Game game = gameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() ->  new GameNotFoundException(gameId));
        logger.info("starting a round in game: " + game.getId());
        game.startGameRound();
        return game;
    }

    public Game sendAnswerToGame(Integer playerId, Answer answer, Integer gameId) throws GameException {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        Game game = gameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() ->  new GameNotFoundException(gameId));
        logger.info("Player:{" + playerId +"} sending answer to game: " + gameId);
        game.sendAnswerToGameRound(player, answer);
        return game;
    }

    public Result evaluateGameRound(Integer gameId) throws GameException {
        Game game = gameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new GameNotFoundException(gameId));
        Result result;
        try {
            result = game.evaluateRound();
        } catch (NoAnswersException e){
            result = new Result("NO ANSWER WAS GIVEN");
            return result;
        }
        logger.info("evaluating the round in game: " + game.getId());
        result.getWinners().forEach(player -> {
                        player.drink();
                        player.setAlcoholPercentage(alcoholCalculatorService.calculateBAC(
                                player.getAlcoholConsumed(),
                                player.getDrinkType(),
                                player.getWeight(),
                                player.getGender()));
                        playerRepository.save(player);
        });
        if(result.isLastQuestion()){
            logger.info("no more round left in game: " + game.getId() + " , removing from the list");
            gameList.remove(game);
        }
        return result;
    }

    public Game getGameById(Integer id) throws GameNotFoundException {
        return gameList.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(() -> new GameNotFoundException(id));
    }

    public Player editPlayer(Integer playedId, DrinkType drinkType, SipType sipType) throws GameException{
        Player playerToModify = playerRepository.findById(playedId).orElseThrow(() -> new PlayerNotFoundException(playedId));
        playerToModify.setDrinkType(drinkType);
        playerToModify.setSipType(sipType);
        return playerRepository.save(playerToModify);
    }

    public List<Game> getGames(){
        return gameList;
    }

}
