package hu.kb.app.api.exceptions;

import hu.kb.app.game.status.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends GameException  {
    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(Integer playerId) {
        super("ERROR:Player not found with id of:" + playerId);
    }
}
