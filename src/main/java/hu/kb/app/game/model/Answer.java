package hu.kb.app.game.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


public @Data @NoArgsConstructor @AllArgsConstructor
class Answer {

    private String answer;

}
