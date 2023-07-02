package view;

import controller.InfernoTowerDefense;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.DragonAttack;
import model.Player;
import model.building.Building;
import model.building.InfernoTower;
import model.hero.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Attack extends Application {
    public Attack(Player attackingPlayer, Player defensivePlayer, ArrayList<Player> players) {
        this.attackingPlayer = attackingPlayer;
        this.defensivePlayer = defensivePlayer;
        this.players = players;
        players.remove(defensivePlayer);
        int count = 0;
        for (Node building : defensivePlayer.getMap().getBuildingsMap()){
            if (building instanceof InfernoTower){
                new InfernoTowerDefense(defensivePlayer.getMap().getMapView(), defensivePlayer.getMap(),(InfernoTower) building).start();
                System.out.println(count++);
            }
        }
    }
    @Override
    public void start(Stage stage) {
        AnchorPane root = attack(stage);
        Scene scene = new Scene(root, 1000, 737);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    private final static DropShadow shadow = new DropShadow(10,Color.GOLD);

    private final Player attackingPlayer;
    private final Player defensivePlayer;
    private final ArrayList<Player> players;

    private AnchorPane attack(Stage stage){
        AnchorPane map = defensivePlayer.getMap().getMapView();

        ImageView imageViewBack = new ImageView("end.png");
        imageViewBack.setX(20);
        imageViewBack.setY(590);
        imageViewBack.setFitWidth(100);
        imageViewBack.setFitHeight(40);
        imageViewBack.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                new PlayerPanel(attackingPlayer, players).start(new Stage());
                stage.close();
            }
        });

        ImageView imageViewDragon = new ImageView("dragon_drag.png");
        imageViewDragon.setX(20);
        imageViewDragon.setY(640);
        imageViewDragon.setFitWidth(79);
        imageViewDragon.setFitHeight(84);

        ImageView imageViewBalloon = new ImageView("balloon_drag.png");
        imageViewBalloon.setX(110);
        imageViewBalloon.setY(642);
        imageViewBalloon.setFitWidth(80);
        imageViewBalloon.setFitHeight(85);

        ImageView imageViewGoblin = new ImageView("goblin_drag.png");
        imageViewGoblin.setX(200);
        imageViewGoblin.setY(642);
        imageViewGoblin.setFitWidth(80);
        imageViewGoblin.setFitHeight(85);

        ImageView imageViewArcher = new ImageView("archer_drag.png");
        imageViewArcher.setX(290);
        imageViewArcher.setY(642);
        imageViewArcher.setFitWidth(80);
        imageViewArcher.setFitHeight(85);


        Rectangle rectangle = new Rectangle(0, 635, 1000, 737-635);
        rectangle.setFill(Color.rgb(0,0,0,0.4));

        AtomicInteger capacityInt = new AtomicInteger(defensivePlayer.getMap().getCapacityMap());

        Text capacity = new Text("Capacity : " + capacityInt);
        capacity.setId("text");
        capacity.setX(810);
        capacity.setY(50);

        ImageView imageViewCapacity = new ImageView("capacity.png");
        imageViewCapacity.setX(800);
        imageViewCapacity.setY(20);
        imageViewCapacity.setFitWidth(150);
        imageViewCapacity.setFitHeight(50);

        imageViewBalloon.setOnMouseClicked(mouseEvent -> {
            imageViewBalloon.setEffect(shadow);
            imageViewArcher.setEffect(null);
            imageViewGoblin.setEffect(null);
            imageViewDragon.setEffect(null);

        });

        imageViewArcher.setOnMouseClicked(mouseEvent -> {
            imageViewBalloon.setEffect(null);
            imageViewArcher.setEffect(shadow);
            imageViewGoblin.setEffect(null);
            imageViewDragon.setEffect(null);

        });

        imageViewGoblin.setOnMouseClicked(mouseEvent -> {
            imageViewBalloon.setEffect(null);
            imageViewArcher.setEffect(null);
            imageViewGoblin.setEffect(shadow);
            imageViewDragon.setEffect(null);
        });

        imageViewDragon.setOnMouseClicked(mouseEvent -> {
            imageViewBalloon.setEffect(null);
            imageViewArcher.setEffect(null);
            imageViewGoblin.setEffect(null);
            imageViewDragon.setEffect(shadow);
        });


        map.setOnMouseClicked(mouseEvent -> {
            if (imageViewBalloon.getEffect() != null && mouseEvent.getY() < 575 && capacityInt.get() >= 10){
                map.getChildren().add(new DefBalloon(mouseEvent.getX()-30, mouseEvent.getY()-35).getImageViews().get(0));
                capacityInt.addAndGet(-10);
                capacity.setText("Capacity : " + capacityInt);
            } else if (imageViewArcher.getEffect() != null  && mouseEvent.getY() < 575 && capacityInt.get() >= 30){
                map.getChildren().add(new ArcherBalloon(mouseEvent.getX()-30, mouseEvent.getY()-40).getImageViews().get(0));
                capacityInt.addAndGet(-30);
                capacity.setText("Capacity : " + capacityInt);
            } else if (imageViewGoblin.getEffect() != null && mouseEvent.getY() < 575 && capacityInt.get() >= 2){
                map.getChildren().add(new GoblinBalloon(mouseEvent.getX()-30, mouseEvent.getY()-40).getImageViews().get(0));
                capacityInt.addAndGet(-2);
                capacity.setText("Capacity : " + capacityInt);
            } else if (imageViewDragon.getEffect() != null && mouseEvent.getY() < 575 && capacityInt.get() >= 20){
                capacityInt.addAndGet(-20);
                capacity.setText("Capacity : " + capacityInt);
                DragonAttack dragonAttack = new DragonAttack(mouseEvent.getX(), mouseEvent.getY(), map, defensivePlayer.getMap());
                Thread threadDragonAttack = new Thread(dragonAttack);
                threadDragonAttack.start();

            }
            if (capacityInt.get() < 30){
                map.getChildren().remove(imageViewArcher);
            }
            if (capacityInt.get() < 20){
                map.getChildren().remove(imageViewDragon);
            }
            if (capacityInt.get() < 10){
                map.getChildren().remove(imageViewBalloon);
            }
            if (capacityInt.get() < 2){
                map.getChildren().remove(imageViewGoblin);
            }
        });

        map.getChildren().addAll(imageViewBack, rectangle, imageViewCapacity, capacity);

        if (attackingPlayer.getLevel() == 1){
            map.getChildren().add(imageViewDragon);
        } else if (attackingPlayer.getLevel() == 2){
            map.getChildren().addAll(imageViewDragon, imageViewBalloon);
        } else if (attackingPlayer.getLevel() == 3){
            map.getChildren().addAll(imageViewDragon, imageViewBalloon, imageViewGoblin);
        } else if (attackingPlayer.getLevel() == 4){
            map.getChildren().addAll(imageViewBalloon, imageViewGoblin, imageViewArcher, imageViewDragon);
        }

        return map;
    }
    public void removeHero(AnchorPane root, Hero hero){
        root.getChildren().remove(hero.getImageViews().get(0));
    }
    public void removeBuilding(AnchorPane root, Building building){
        root.getChildren().remove(building.getImageView());
    }
}
