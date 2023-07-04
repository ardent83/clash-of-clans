package controller;

import data.UpdatePlayerData;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Map.Map;
import model.Player;
import view.PlayerPanel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckResultAttack extends Thread {
    public CheckResultAttack(Stage stage, AnchorPane root, Player attackingPlayer, Player defensivePlayer, AtomicInteger capacityInt, ArrayList<Player> players) {
        this.map = defensivePlayer.getMap();
        this.firstSize = defensivePlayer.getMap().getBuildingsMap().size();
        this.root = root;
        this.attackingPlayer = attackingPlayer;
        this.defensivePlayer = defensivePlayer;
        this.capacityInt = capacityInt;
        this.stage = stage;
        this.players = players;
        this.starView = new ImageView();
        starView.setX(231);
        starView.setY(150);
        starView.setFitWidth(537);
        starView.setFitHeight(220);

        this.viewBack = new ImageView();
        viewBack.setX(450);
        viewBack.setY(500);
        viewBack.setFitWidth(100);
        viewBack.setFitHeight(43);
    }

    private final Map map;
    private final int firstSize;
    private final AnchorPane root;
    private final AtomicInteger capacityInt;
    private final Player attackingPlayer;
    private final Player defensivePlayer;
    private final ArrayList<Player> players;
    private final Stage stage;
    private final ImageView starView;
    private final ImageView viewBack;

    @Override
    public synchronized void run() {
        Platform.runLater(() -> {
            root.getChildren().add(viewBack);
            root.getChildren().add(starView);
            viewBack.setOnMouseClicked(mouseEvent -> {
                new PlayerPanel(attackingPlayer, players).start(new Stage());
                stage.close();
            });
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            if (defensivePlayer.getMap().getBuildingsMap().size() == 0){
                defensivePlayer.setNumberLose(defensivePlayer.getNumberLose()+1);
                attackingPlayer.setNumberWin(attackingPlayer.getNumberWin()+1);
                if (defensivePlayer.getLevel() > 1){
                    defensivePlayer.setLevel(defensivePlayer.getLevel()-1);
                }
                if (attackingPlayer.getLevel() < 4){
                    attackingPlayer.setLevel(attackingPlayer.getLevel()+1);
                }
                new UpdatePlayerData(attackingPlayer).start();
                new UpdatePlayerData(defensivePlayer).start();
                Platform.runLater(() -> {
                    starView.setImage(new Image("star3.png"));
                    viewBack.setImage(new Image("back_home.png"));
                    myNotify();
                });
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            } else if (capacityInt.get() == 0 && map.getAttackingHeroes().size() == 0){
                defensivePlayer.setNumberWin(defensivePlayer.getNumberWin()+1);
                attackingPlayer.setNumberLose(attackingPlayer.getNumberLose()+1);
                if (defensivePlayer.getLevel() < 4){
                    defensivePlayer.setLevel(defensivePlayer.getLevel()+1);
                }
                if (attackingPlayer.getLevel() > 1){
                    attackingPlayer.setLevel(attackingPlayer.getLevel()-1);
                }
                new UpdatePlayerData(attackingPlayer).start();
                new UpdatePlayerData(defensivePlayer).start();
                Platform.runLater(() -> {
                    starView.setImage(new Image("star2.png"));
                    myNotify();
                });
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void myNotify(){
        notify();
    }
}
