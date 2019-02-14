package hu.kb.app.service;

import hu.kb.app.game.quiz.Answer;
import hu.kb.app.game.quiz.Question;
import hu.kb.app.game.RareGame;
import hu.kb.app.player.Player;
import hu.kb.app.repository.PlayerRepository;
import hu.kb.app.repository.RareGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    private RareGameRepository rareGameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public RareGame createGame(){
        return rareGameRepository.save(new RareGame());
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

    public RareGame startGame(Integer id){
        RareGame rareGame = rareGameRepository.findById(id).get();
        Question question = rareGame.startGameCycle();
        return rareGame;
    }

    public RareGame sendAnswerToGame(Answer answer){
        RareGame rareGame = rareGameRepository.findById(answer.getGameId()).get();
        rareGame.sendAnswerToGameCycle(answer);
        return rareGame;
    }

    public String evaluateGameCycle(Integer id){
        return rareGameRepository.findById(id).get().evaluteCycle();
    }




}
