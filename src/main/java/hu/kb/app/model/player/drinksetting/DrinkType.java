package hu.kb.app.model.player.drinksetting;

public enum DrinkType {
    WHISKEY(40),VODKA(35),BEER(4),WINE(8);

    private int alcoholPrecentage;

    DrinkType(int alcoholPrecentage) {
        this.alcoholPrecentage = alcoholPrecentage;
    }

    public int getAlcoholPrecentage() {
        return alcoholPrecentage;
    }

    public DrinkType getRandom(){
        return DrinkType.values()[(int)(Math.random() * DrinkType.values().length)];
    }

}
