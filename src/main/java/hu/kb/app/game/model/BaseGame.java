package hu.kb.app.game.model;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.game.enums.Status;
import hu.kb.app.game.hurrygame.HurryGameGameRound;
import hu.kb.app.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class BaseGame {

    protected String name;

    protected Integer id;

    protected List<Player> players  = new ArrayList<>();

    protected Status status;

    public void addPlayer(Player player){
        this.players.add(player);
    }



}
