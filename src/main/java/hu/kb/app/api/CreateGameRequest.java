package hu.kb.app.api;

import hu.kb.app.game.enums.GameType;
import hu.kb.app.game.model.Question;
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
