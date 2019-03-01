package hu.kb.app.player;

import hu.kb.app.api.CreatePlayerRequest;
import hu.kb.app.player.drinksetting.DrinkType;
import hu.kb.app.player.drinksetting.SipType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public @Data
@ToString
class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private double weight;
    private DrinkType drinkType;
    private SipType sipType = SipType.MEDIUM;
    private Double alcoholConsumed = 0.0;
    private Double alcoholPercentage = 0.0;
    private Gender gender;


    public Player(){}

    public void drink(){
        this.alcoholConsumed += sipType.getMl();
    }

    public Player(String name, double weight, DrinkType drinkType) {
        this.name = name;
        this.weight = weight;
        this.drinkType = drinkType;
    }

    public Player(String name, double weight, DrinkType drinkType, SipType sipType, Gender gender) {
        this.name = name;
        this.weight = weight;
        this.drinkType = drinkType;
        this.sipType = sipType;
        this.gender = gender;
    }


}
