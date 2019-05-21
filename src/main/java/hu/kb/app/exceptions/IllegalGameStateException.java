package hu.kb.app.exceptions;

import hu.kb.app.model.game.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalGameStateException extends GameException {
    public IllegalGameStateException(String message) {
        super(message);
    }

    public IllegalGameStateException(Status status) {
        super("ERROR:" +
                "The game is in " + status.name() + "status");
    }
}
