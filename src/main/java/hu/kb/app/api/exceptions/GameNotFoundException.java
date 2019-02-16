package hu.kb.app.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends GameException  {
    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(Integer gameId) {
        super("ERROR:game not found with id of:" + gameId);
    }
}
