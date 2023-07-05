package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchOpponents extends Application {
    public SearchOpponents(Player player, ArrayList<Player> players) {
        this.player = player;
        this.players = players;
    }

    private final ArrayList<Player> players;
    private final Player player;
    private Scene scene;
    private final AtomicInteger count = new AtomicInteger(0);
    @Override
    public void start(Stage stage) {
        scene = new Scene(getMap(stage, players.get(0).getMap().getMapView()), 1000, 737);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setTitle("Searching Opponents");
        stage.getIcons().add(new Image("icon.jpg"));
        stage.show();
        stage.setResizable(false);
    }
    private AnchorPane getMap(Stage stage, AnchorPane map){

        ImageView imageViewBack = new ImageView("end.png");
        imageViewBack.setX(20);
        imageViewBack.setY(590);
        imageViewBack.setFitWidth(100);
        imageViewBack.setFitHeight(40);
        imageViewBack.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 ){
                new PlayerPanel(player, players).start(new Stage());
                stage.close();
            }
        });

        ImageView levelView;
        if (players.get(count.get()).getLevel() == 1){
            levelView = new ImageView("level_1.png");
        } else if (players.get(count.get()).getLevel() == 2){
            levelView = new ImageView("level_2.png");
        } else if (players.get(count.get()).getLevel() == 3){
            levelView = new ImageView("level_3.png");
        } else {
            levelView = new ImageView("level_4.png");
        }

        levelView.setX(15);
        levelView.setY(15);
        levelView.setFitWidth(70);
        levelView.setFitHeight(70);

        map.getChildren().add(levelView);

        ImageView nameView = new ImageView("back_name.png");
        nameView.setX(84);
        nameView.setY(29);
        nameView.setFitHeight(40);

        Text textName = new Text(players.get(count.get()).getIdPlayer());
        textName.setId("text");
        textName.setX(90);
        textName.setY(55);

        map.getChildren().addAll(nameView, textName);

        ImageView imageViewNext = new ImageView("next.png");
        imageViewNext.setX(850);
        imageViewNext.setY(580);
        imageViewNext.setFitWidth(100);
        imageViewNext.setFitHeight(50);
        imageViewNext.setOnMouseClicked(mouseEvent -> {
            if (players.size()-1 > count.get()){
                if (players.get(count.get()).equals(player)) {
                    count.addAndGet(1);
                }
                scene = new Scene(getMap(stage,players.get(count.addAndGet(1)).getMap().getMapView()), 1000, 737);
                scene.getStylesheets().add("style.css");
                stage.setScene(scene);
            } else {
                map.getChildren().remove(imageViewNext);
            }
        });

        ImageView attackView = new ImageView("attack1.png");
        attackView.setX(730);
        attackView.setY(590);
        attackView.setFitWidth(100);
        attackView.setFitHeight(40);
        attackView.setOnMouseClicked(mouseEvent -> {
            new Attack(player, players.get(count.get()), players).start(new Stage());
            stage.close();
        });

        bottomHero(map);
        map.getChildren().addAll(imageViewBack, attackView, imageViewNext);

        return map;
    }

    private void bottomHero(AnchorPane map){
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



        AtomicInteger capacityInt = new AtomicInteger(players.get(count.get()).getMap().getCapacityMap());

        Text capacity = new Text("Capacity : " + capacityInt);
        capacity.setId("text");
        capacity.setX(810);
        capacity.setY(50);

        ImageView imageViewCapacity = new ImageView("capacity.png");
        imageViewCapacity.setX(800);
        imageViewCapacity.setY(20);
        imageViewCapacity.setFitWidth(150);
        imageViewCapacity.setFitHeight(50);

        map.getChildren().addAll(rectangle, imageViewCapacity, capacity);


        if (player.getLevel() == 1){
            map.getChildren().add(imageViewDragon);
        } else if (player.getLevel() == 2){
            map.getChildren().addAll(imageViewDragon, imageViewBalloon);
        } else if (player.getLevel() == 3){
            map.getChildren().addAll(imageViewDragon, imageViewBalloon, imageViewGoblin);
        } else if (player.getLevel() == 4){
            map.getChildren().addAll(imageViewBalloon, imageViewGoblin, imageViewArcher, imageViewDragon);
        }
    }
}
