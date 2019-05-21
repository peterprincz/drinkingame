package hu.kb.app.model.player.drinksetting;

public enum SipType {
    BIG(60.0),MEDIUM(30.0),SMALL(10.0), SHOT(20.0),BIGSHOT(40.0);

    private Double ml;

    SipType(Double ml) {
        this.ml =  ml;
    }

    public Double getMl() {
        return  ml;
    }

}
