package hu.kb.app.model.hurrygame;

import hu.kb.app.exceptions.GameException;
import hu.kb.app.model.game.basegame.Game;
import hu.kb.app.model.game.enums.Status;
import hu.kb.app.model.game.basegame.Answer;
import hu.kb.app.model.game.basegame.BaseGame;
import hu.kb.app.model.game.basegame.Question;
import hu.kb.app.model.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor
class HurryGame extends BaseGame implements Game {

    public void addQuestion(Question question){
        gameRoundList.add(new HurryGameGameRound(question));
    }

}
