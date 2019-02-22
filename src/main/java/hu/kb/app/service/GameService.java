package hu.kb.app.service;

import hu.kb.app.api.CreateGameRequest;
import hu.kb.app.api.exceptions.GameException;
import hu.kb.app.api.exceptions.GameNotFoundException;
import hu.kb.app.api.exceptions.PlayerNotFoundException;
import hu.kb.app.game.RareGameFactory;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.RareGame;
import hu.kb.app.game.quiz.Result;
import hu.kb.app.player.Player;
import hu.kb.app.repository.PlayerRepository;
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

    private List<RareGame> rareGameList = new ArrayList<>();


    public List<RareGame> getGames(){
        return rareGameList;
    }

    public RareGame createGame(CreateGameRequest createGameRequest){
        RareGame rareGame;
        if(createGameRequest.getQuestions() == null || createGameRequest.getQuestions().isEmpty()) {
            rareGame = RareGameFactory.createRareGameWithDefaultQuestions(createGameRequest.getGameName());
        } else {
            rareGame = RareGameFactory.createRareGame(createGameRequest.getQuestions(), createGameRequest.getGameName());
        }
        this.rareGameList.add(rareGame);
        return rareGame;
    }

    public Player createPlayer(Player player){
        return playerRepository.save(player);
    }

    public RareGame joinGame(Integer playerId,Integer gameId) throws GameException{
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new GameNotFoundException(gameId));
        rareGame.addPlayer(playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId)));
        return rareGame;
    }

    public RareGame startGameCycle(Integer gameId) throws GameException {
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() ->  new GameNotFoundException(gameId));
        rareGame.startGameCycle();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Integer playerId, Answer answer) throws GameException {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(answer.getGameId())).findFirst().orElseThrow(() ->  new GameNotFoundException(answer.getGameId()));
        rareGame.sendAnswerToGameCycle(player, answer);
        return rareGame;
    }

    public Result evaluateGameCycle(Integer gameId) throws GameException {
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new GameNotFoundException(gameId));
        Result result = rareGame.evaluteCycle();
        result.getWinners().forEach(player -> {
                        player.drink();
                        player.setAlcoholPercentage(alcoholCalculatorService.calculateBAC(
                                player.getAlcoholConsumed(),
                                player.getDrinkType(),
                                player.getWeight(),
                                "MALE"));
                        playerRepository.save(player);
        });
        //Update DB
        if(result.isLastQuestion()){
            rareGameList.remove(rareGame);
        }
        return result;
    }

    public RareGame getGameById(Integer id) throws GameNotFoundException {
        return rareGameList.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(() -> new GameNotFoundException(id));
    }
}
