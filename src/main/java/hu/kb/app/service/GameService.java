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

    public RareGame startGame(Player host){
        RareGame rareGame = rareGameMap.get(host);
        if(rareGame == null){
            throw new RuntimeException("Host doesn't have a game");
        }
        Question question = rareGame.startGameCycle();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Answer answer){
        for(RareGame rareGame : rareGameMap.values()){
            if(rareGame.getId().equals(answer.getGameId())){
                rareGame.sendAnswerToGameCycle(answer);
                return rareGame;
            }
        }
        throw  new RuntimeException("Game not found with the id of:" + answer.getGameId());
    }

    public String getWinner(Player player){
        return rareGameMap.get(player).getWinner();
    }




}
