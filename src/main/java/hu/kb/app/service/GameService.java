package hu.kb.app.service;

import hu.kb.app.game.Answer;
import hu.kb.app.game.Question;
import hu.kb.app.game.RareGame;
import hu.kb.app.player.Player;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {


    private Map<Player, RareGame> rareGameMap = new HashMap<>();

    public RareGame createGame(Player host){
        RareGame rareGame = rareGameMap.get(host);
        if(rareGame != null){
            throw new RuntimeException("Host already has a game");
        }
        rareGameMap.put(host, new RareGame());
        return rareGameMap.get(host);
    }


    public Question startGame(Player host){
        RareGame rareGame = rareGameMap.get(host);
        if(rareGame == null){
            throw new RuntimeException("Host doesn't have a game");
        }
         return rareGame.startGameCycle();
    }

    public RareGame lockGame(Player host){
        RareGame rareGame = rareGameMap.get(host);
        if( rareGame == null){
            throw new RuntimeException("Host doesn't have a game");
        }
        rareGame.lockGame();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Answer answer, Integer gameId){
        for(RareGame rareGame : rareGameMap.values()){
            if(rareGame.getId().equals(gameId)){
                rareGame.sendAnswerToGameCycle(answer);
                return rareGame;
            }
        }
        throw  new RuntimeException("Game not found with the id of:" + gameId);
    }

    public String getWinner(Player player){
        return rareGameMap.get(player).getWinner();
    }




}
