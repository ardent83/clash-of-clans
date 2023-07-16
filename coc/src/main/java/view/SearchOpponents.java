package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchOpponents extends Application {
    public SearchOpponents(Player player, ArrayList<Player> players, MediaPlayer mediaPlayer) {
        this.player = player;
        this.players = players;
        this.mediaPlayer = mediaPlayer;
    }

    private final ArrayList<Player> players;
    private final Player player;
    private Scene scene;
    private final AtomicInteger count = new AtomicInteger(0);
    private final MediaPlayer mediaPlayer;
    private final File audioFileClick = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\click_button.mp3");
    private final String audioFilePath = audioFileClick.toURI().toString();
    private final MediaPlayer mediaPlayerClick = new MediaPlayer(new Media(audioFilePath));
    @Override
    public void start(Stage stage) {
        if (players.get(count.get()).equals(player)) {
            count.addAndGet(1);
        }
        scene = new Scene(getMap(stage, players.get(count.get()).getMap().getMapView()), 1000, 737);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setTitle("Searching Opponents");
        stage.getIcons().add(new Image("icon.jpg"));
        stage.show();
        stage.setResizable(false);

    }
    private AnchorPane getMap(Stage stage, AnchorPane map){

        ImageView imageViewBack = new ImageView("back_home.png");
        imageViewBack.setX(20);
        imageViewBack.setY(590);
        imageViewBack.setFitWidth(100);
        imageViewBack.setFitHeight(40);
        imageViewBack.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mediaPlayerClick.stop();
            new PlayerPanel(player, players).start(new Stage());
            Platform.runLater(mediaPlayer::stop);
            stage.close();
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
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mediaPlayerClick.stop();
            if (players.size()-3 > count.get()){
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
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mediaPlayerClick.stop();
            new Attack(player, players.get(count.get()), players).start(new Stage());
            Platform.runLater(mediaPlayer::stop);
            stage.close();
        });

        bottomHero(map);
        map.getChildren().addAll(imageViewBack, attackView, imageViewNext, new MediaView(mediaPlayerClick));

        return map;
    }

    private void bottomHero(AnchorPane map){
        ImageView imageViewDragon = new ImageView("dragon_drag.png");
        imageViewDragon.setX(20);
        imageViewDragon.setY(640);
        imageViewDragon.setFitWidth(79);
        imageViewDragon.setFitHeight(84);

        ImageView imageViewPanda = new ImageView("panda_drag.png");
        imageViewPanda.setX(110);
        imageViewPanda.setY(642);
        imageViewPanda.setFitWidth(80);
        imageViewPanda.setFitHeight(83);

        ImageView imageViewBalloon = new ImageView("balloon_drag.png");
        imageViewBalloon.setX(200);
        imageViewBalloon.setY(642);
        imageViewBalloon.setFitWidth(80);
        imageViewBalloon.setFitHeight(85);

        ImageView imageViewGoblin = new ImageView("goblin_drag.png");
        imageViewGoblin.setX(290);
        imageViewGoblin.setY(642);
        imageViewGoblin.setFitWidth(80);
        imageViewGoblin.setFitHeight(85);

        ImageView imageViewArcher = new ImageView("archer_drag.png");
        imageViewArcher.setX(380);
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
            map.getChildren().addAll(imageViewDragon, imageViewPanda);
        } else if (player.getLevel() == 2){
            map.getChildren().addAll(imageViewDragon,imageViewPanda, imageViewBalloon);
        } else if (player.getLevel() == 3){
            map.getChildren().addAll(imageViewDragon, imageViewPanda, imageViewBalloon, imageViewGoblin);
        } else if (player.getLevel() == 4){
            map.getChildren().addAll(imageViewBalloon, imageViewPanda, imageViewGoblin, imageViewArcher, imageViewDragon);
        }
    }
}
