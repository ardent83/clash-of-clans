package controller;

import data.UpdatePlayerData;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Map.Map;
import model.Player;
import view.PlayerPanel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckResultAttack extends Thread {
    public CheckResultAttack(Stage stage, Player attackingPlayer, Player defensivePlayer, AtomicInteger capacityInt, ArrayList<Player> players) {
        this.map = defensivePlayer.getMap();
        this.attackingPlayer = attackingPlayer;
        this.defensivePlayer = defensivePlayer;
        this.capacityInt = capacityInt;
        this.stage = stage;
        this.players = players;
    }

    private final Map map;
    private final AtomicInteger capacityInt;
    private final Player attackingPlayer;
    private final Player defensivePlayer;
    private final ArrayList<Player> players;
    private final Stage stage;

    @Override
    public synchronized void run() {
        while (true){
            if (defensivePlayer.getMap().getBuildingsMap().size() == 0){
                defensivePlayer.setNumberLose(defensivePlayer.getNumberLose()+1);
                attackingPlayer.setNumberWin(attackingPlayer.getNumberWin()+1);
                new UpdatePlayerData(attackingPlayer).start();
                new UpdatePlayerData(defensivePlayer).start();
                Platform.runLater(() -> {
                    new PlayerPanel(attackingPlayer, players).start(new Stage());
                    stage.close();
                });
                break;
            } else if (capacityInt.get() == 0 && defensivePlayer.getMap().getAttackingHeroes().size() == 0){
                defensivePlayer.setNumberWin(defensivePlayer.getNumberWin()+1);
                attackingPlayer.setNumberLose(attackingPlayer.getNumberLose()+1);
                new UpdatePlayerData(attackingPlayer).start();
                new UpdatePlayerData(defensivePlayer).start();
                Platform.runLater(() -> {
                    new PlayerPanel(attackingPlayer, players).start(new Stage());
                    stage.close();
                });
                break;
            }
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
