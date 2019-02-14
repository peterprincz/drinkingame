package hu.kb.app.player.drinksetting;

public enum DrinkType {
    WHISKEY(40),VODKA(35),BEER(4),WINE(8);

    private int alcoholPrecentage;

    DrinkType(int alcoholPrecentage) {
        this.alcoholPrecentage = alcoholPrecentage;
    }

    public int getAlcoholPrecentage() {
        return alcoholPrecentage;
    }

}