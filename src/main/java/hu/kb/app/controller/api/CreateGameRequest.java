package hu.kb.app.controller.api;

import hu.kb.app.model.game.enums.GameType;
import hu.kb.app.model.game.basegame.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public @Data
@NoArgsConstructor
class CreateGameRequest {

    private String gameName;

    private List<Question> questions;

    private GameType gameType;
}
