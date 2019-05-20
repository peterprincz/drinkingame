package hu.kb.app.game.hurrygame;

import hu.kb.app.game.model.Answer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HurryGameAnswer extends Answer {
    LocalDateTime submitTime;

    public HurryGameAnswer(String answer) {
        super(answer);
        this.submitTime = LocalDateTime.now();
    }
}
