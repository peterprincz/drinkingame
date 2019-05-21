package hu.kb.app.model.game.basegame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.exceptions.IllegalGameStateException;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class BaseGameRound {

    protected List<Player> players = new ArrayList<>();
    protected Status status;
    protected Question question;

    public BaseGameRound(Question question){
        this.status = Status.CREATED;
        this.question = question;
    }

    public void join(Player player){
        this.players.add(player);
    }

    public Question start() throws GameException {
        if(this.status != Status.CREATED){
            throw new IllegalGameStateException(status);
        }
        this.status = Status.ONGOING;
        return this.question;
    }

    public void handleAnswer(Player player, Answer answer) throws GameException {
        if (status != Status.ONGOING) {
            throw new IllegalGameStateException(status);
        }
    }

}
