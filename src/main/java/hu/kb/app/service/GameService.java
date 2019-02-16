package hu.kb.app.service;

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

    private List<RareGame> rareGameList = new ArrayList<>();


    public List<RareGame> getGames(){
        return rareGameList;
    }

    public RareGame createGame(String gameName){
        RareGame rareGame = RareGameFactory.createRareGameWithDefaultQuestions(gameName);
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
        return rareGame.evaluteCycle();
    }

}
