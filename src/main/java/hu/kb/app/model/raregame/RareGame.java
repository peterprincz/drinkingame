package hu.kb.app.model.raregame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.model.game.basegame.Game;
import hu.kb.app.model.game.basegame.Answer;
import hu.kb.app.model.game.basegame.BaseGame;
import hu.kb.app.model.game.basegame.Question;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
public @Data
class RareGame extends BaseGame implements Game {

    public RareGame() {
    }

    public void addQuestion(Question question){
        gameRoundList.add(new RareGameRound(question));
    }

}