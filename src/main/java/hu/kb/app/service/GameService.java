package hu.kb.app.service;

import hu.kb.app.game.gamecycle.RareGameCycle;
import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.RareGame;
import hu.kb.app.player.Player;
import hu.kb.app.repository.PlayerRepository;
import hu.kb.app.repository.RareGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    private RareGameRepository rareGameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    private Map<Integer, List<RareGameCycle>> gameCycles = new HashMap<>();

    public List<RareGame> getGames(){
        List<RareGame> result = new ArrayList<>();
        rareGameRepository.findAll().forEach(result::add);
        return result;
    }

    public RareGame createGame(){
        RareGame rareGame = rareGameRepository.save(new RareGame());
        gameCycles.put(rareGame.getId(),new ArrayList<>(Arrays.asList(new RareGameCycle(new Question("What the fuck am i doing here?", Arrays.asList("i dont know","no idea","ask someone else"))))));
        return rareGame;
    }

    public Player createPlayer(Player player){
        return playerRepository.save(player);
    }

    public RareGame joinGame(Integer playerId,Integer id){
        RareGame rareGame = rareGameRepository.findById(id).get();
        Player player = playerRepository.findById(playerId).get();
        rareGame.addPlayer(player);
        return rareGameRepository.save(rareGame);
    }

    public RareGame startGameCycle(Integer id){
        RareGame rareGame = rareGameRepository.findById(id).get();
        Question question = rareGame.startGameCycle(gameCycles.get(id));
        return rareGameRepository.save(rareGame);
    }

    public RareGame sendAnswerToGame(Answer answer){
        RareGame rareGame = rareGameRepository.findById(answer.getGameId()).get();
        rareGame.sendAnswerToGameCycle(answer, gameCycles.get(rareGame.getId()));
        return rareGame;
    }

    public String evaluateGameCycle(Integer id){
        return rareGameRepository.findById(id).get().evaluteCycle(gameCycles.get(id));
    }




}
