package hu.kb.app.player;

import hu.kb.app.player.drinksetting.DrinkType;
import hu.kb.app.player.drinksetting.SipType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public @Data
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



    public Player(){}

    public void drink(){
        this.alcoholConsumed += sipType.getMl();
    }

    public Player(String name, double weight, DrinkType drinkType) {
        this.name = name;
        this.weight = weight;
        this.drinkType = drinkType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", drinkType=" + drinkType +
                ", alcoholConsumed=" + alcoholConsumed +
                '}';
    }
}
