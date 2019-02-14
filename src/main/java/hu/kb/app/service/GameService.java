package hu.kb.app.service;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.RareGame;
import hu.kb.app.player.Player;
import hu.kb.app.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    private PlayerRepository playerRepository;

    List<RareGame> rareGameList = new ArrayList<>();


    public List<RareGame> getGames(){
        return rareGameList;
    }

    public RareGame createGame(){
        RareGame rareGame = new RareGame();
        rareGame.generateAndSetId();
        rareGame.fillWithQuestions(Arrays.asList(
                new Question("What the fuck am i doing here?", Arrays.asList("i dont know","no idea","ask someone else")),
                new Question("What the fuck am i doing here?", Arrays.asList("i dont know","no idea","ask someone else"))
        ));
        rareGameList.add(rareGame);
        return rareGame;
    }

    public Player createPlayer(Player player){
        return playerRepository.save(player);
    }

    public RareGame joinGame(Integer playerId,Integer id){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        rareGame.addPlayer(playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("No such User")));
        return rareGame;
    }

    public RareGame startGameCycle(Integer id){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        Question question = rareGame.startGameCycle();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Answer answer){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(answer.getGameId())).findFirst().get();
        rareGame.sendAnswerToGameCycle(answer);
        return rareGame;
    }

    public String evaluateGameCycle(Integer id){
        RareGame rareGame = rareGameList.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        return rareGame.evaluteCycle();
    }

}
