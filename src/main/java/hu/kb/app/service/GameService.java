package hu.kb.app.service;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.GameNotFoundException;
import hu.kb.app.exceptions.NoAnswersException;
import hu.kb.app.exceptions.PlayerNotFoundException;
import hu.kb.app.controller.GameController;
import hu.kb.app.game.RareGameFactory;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.RareGame;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.quiz.Result;
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

    private List<RareGame> rareGameList = new ArrayList<>();

    public List<RareGame> getGames(){
        return rareGameList;
    }

    public RareGame createGame(String gameName, List<Question> questions){
        RareGame rareGame;
        if(questions == null || questions.isEmpty()) {
            logger.info("Creating game with default questions:" + gameName);
            rareGame = RareGameFactory.createRareGameWithDefaultQuestions(gameName);
        } else {
            logger.info("Creating game with provided questions:" + gameName);
            rareGame = RareGameFactory.createRareGame(questions, gameName);
        }
        logger.info("Adding game to the gameList");
        this.rareGameList.add(rareGame);
        logger.info("Successfully added game: " + rareGame.getName());
        logger.info("Number of games: " + rareGameList.size());
        return rareGame;
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

    public RareGame joinGame(Integer playerId,Integer gameId) throws GameException{
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new GameNotFoundException(gameId));
        logger.info("Player:{" + playerId +"} joining game: " + gameId);
        rareGame.addPlayer(playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId)));
        logger.info("Player:{" + playerId +"} successfully joined game: " + gameId);
        return rareGame;
    }

    public RareGame startGameCycle(Integer gameId) throws GameException {
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() ->  new GameNotFoundException(gameId));
        logger.info("starting a round in game: " + rareGame.getId());
        rareGame.startGameRound();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Integer playerId, Answer answer) throws GameException {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(answer.getGameId())).findFirst().orElseThrow(() ->  new GameNotFoundException(answer.getGameId()));
        logger.info("Player:{" + playerId +"} sending answer to game: " + answer.getGameId());
        rareGame.sendAnswerToGameRound(player, answer);
        return rareGame;
    }

    public Result evaluateGameCycle(Integer gameId) throws GameException {
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new GameNotFoundException(gameId));
        Result result;
        try {
            result = rareGame.evaluateRound();
        } catch (NoAnswersException e){
            result = new Result();
            result.setResult("NO ANSWER WAS GIVEN");
            return result;
        }
        logger.info("evaluating the round in game: " + rareGame.getId());
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
            logger.info("no more round left in game: " + rareGame.getId() + " , removing from the list");
            rareGameList.remove(rareGame);
        }
        return result;
    }

    public RareGame getGameById(Integer id) throws GameNotFoundException {
        return rareGameList.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(() -> new GameNotFoundException(id));
    }

    public Player editPlayer(Integer playedId, DrinkType drinkType, SipType sipType) throws GameException{
        Player playerToModify = playerRepository.findById(playedId).orElseThrow(() -> new PlayerNotFoundException(playedId));
        playerToModify.setDrinkType(drinkType);
        playerToModify.setSipType(sipType);
        return playerRepository.save(playerToModify);
    }
}
