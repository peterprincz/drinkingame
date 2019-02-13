package hu.kb.app.game;

import hu.kb.app.player.Player;

public class Main {
    public static void main(String[] args) {
        RareGame rareGame = new RareGame();
        Player player1 = new Player("pista");
        Player player2 = new Player("karcsi");
        Player player3 = new Player("kocka");
        Player player4 = new Player("jozsi");
        rareGame.addPlayer(player1);
        rareGame.addPlayer(player2);
        rareGame.addPlayer(player3);
        rareGame.addPlayer(player4);
        rareGame.lockGame();

        Question question = rareGame.startGameCycle();
        System.out.println("The question is" + question);
        rareGame.sendAnswerToGameCycle(new Answer(player1,"i dont know"));
        rareGame.sendAnswerToGameCycle(new Answer(player2,"i dont know"));
        rareGame.sendAnswerToGameCycle(new Answer(player3,"i dont know"));
        rareGame.sendAnswerToGameCycle(new Answer(player4,"no idea"));

        System.out.println(rareGame.getWinner());
    }
}
