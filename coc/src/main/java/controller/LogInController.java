package controller;


import model.Player;

import java.util.ArrayList;

public class LogInController {
    public LogInController(ArrayList<Player> players) {
        this.players = players;
    }

    private final ArrayList<Player> players;

    public Player getPlayer(String idPlayer, String password) throws Exception {
        for (Player player : players){
            if (player.getIdPlayer().equals(idPlayer) && player.getPassword().equals(password)){
                return player;
            }
        }
        throw new Exception("username or password is wrong!");
    }
}
