package hu.kb.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoAnswersException extends GameException {


    public NoAnswersException(String message) {
        super(message);
    }

    public NoAnswersException(Integer gameId) {
        super("ERROR:The game with the id of " + gameId + " doesn't have any anwers to evaluate");
    }
}
