package hu.kb.app.repository;

import hu.kb.app.game.RareGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RareGameRepository extends CrudRepository<RareGame,Integer> {
}
