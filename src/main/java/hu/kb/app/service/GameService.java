package hu.kb.app.service;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
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
        RareGame rareGame = new RareGame();
        rareGame.setName(gameName);
        rareGame.generateAndSetId();
        rareGame.fillWithQuestions(Arrays.asList(
                new Question("What the fuck am i doing here?",Arrays.asList("hoppá","hopp")),
                new Question("What the fuck am i doing here?",Arrays.asList("dik","komoly"))
        ));
        rareGameList.add(rareGame);
        return rareGame;
    }

    public Player createPlayer(Player player){
        return playerRepository.save(player);
    }

    public RareGame joinGame(Integer playerId,Integer gameId){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new RuntimeException("Game not found with the id of" + gameId));
        rareGame.addPlayer(playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found with the id of" + playerId)));
        return rareGame;
    }

    public RareGame startGameCycle(Integer gameId){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new RuntimeException("Game not found with the id of" + gameId));
        rareGame.startGameCycle();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Integer playerId, Answer answer){
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found with the id of" + playerId));
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(answer.getGameId())).findFirst().orElseThrow(RuntimeException::new);
        rareGame.sendAnswerToGameCycle(player, answer);
        return rareGame;
    }

    public Result evaluateGameCycle(Integer gameId){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(gameId)).findFirst().orElseThrow(() -> new RuntimeException("Game not found with the id of" + gameId));
        return rareGame.evaluteCycle();
    }

}
