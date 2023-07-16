package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import model.hero.ArcherBalloon;
import model.hero.DefBalloon;
import model.hero.Dragon;
import model.hero.GoblinBalloon;

import java.io.File;
import java.util.ArrayList;

public class PlayerPanel extends Application {
    public PlayerPanel(Player player, ArrayList<Player> players) {
        this.player = player;
        this.players = players;
        File audioFileDie = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\player_panel.mp3");
        String audioFilePathDie = audioFileDie.toURI().toString();
        mediaPlayer = new MediaPlayer(new Media(audioFilePathDie));
    }

    private final Player player;
    private final ArrayList<Player> players;
    private final MediaPlayer mediaPlayer;
    private final File audioFileClick = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\click_button.mp3");
    private final String audioFilePath = audioFileClick.toURI().toString();
    private final MediaPlayer mediaPlayerClick = new MediaPlayer(new Media(audioFilePath));
    @Override
    public void start(Stage stage) {
        AnchorPane root = root(stage);
        Scene scene = new Scene(root, 1000, 737);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.getIcons().add(new Image("icon.jpg"));
        stage.show();
        stage.setResizable(false);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(100000);
        mediaPlayer.play();
        root.getChildren().addAll(new MediaView(mediaPlayer), new MediaView(mediaPlayerClick));
    }

    AnchorPane root(Stage stage){
        AnchorPane root = player.getMap().getMapView();

        ImageView attackView = new ImageView("attack.png");
        attackView.setX(20);
        attackView.setY(620);
        attackView.setFitWidth(100);
        attackView.setFitHeight(100);
        attackView.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new SearchOpponents(player, players, mediaPlayer).start(new Stage());
            stage.close();
        });
        root.getChildren().add(attackView);
        ImageView levelView;
        if (player.getLevel() == 1){
            levelView = new ImageView("level_1.png");
        } else if (player.getLevel() == 2){
            levelView = new ImageView("level_2.png");
        } else if (player.getLevel() == 3){
            levelView = new ImageView("level_3.png");
        } else {
            levelView = new ImageView("level_4.png");
        }

        levelView.setX(15);
        levelView.setY(15);
        levelView.setFitWidth(70);
        levelView.setFitHeight(70);

        root.getChildren().add(levelView);

        ImageView nameView = new ImageView("back_name.png");
        nameView.setX(84);
        nameView.setY(29);
        nameView.setFitHeight(40);

        Text textName = new Text(player.getIdPlayer());
        textName.setId("text");
        textName.setX(90);
        textName.setY(55);

        root.getChildren().addAll(nameView, textName);

        ImageView winView = new ImageView("win.png");
        winView.setX(700);
        winView.setY(20);
        winView.setFitWidth(120);
        winView.setFitHeight(50);
        winView.setEffect(new DropShadow());
        Text textWin = new Text("Win : " + player.getNumberWin());
        textWin.setId("text");
        textWin.setX(710);
        textWin.setY(50);


        ImageView loseView = new ImageView("lose.png");
        loseView.setX(830);
        loseView.setY(20);
        loseView.setFitWidth(120);
        loseView.setFitHeight(50);
        loseView.setEffect(new DropShadow());
        Text textLose = new Text("Lose : " + player.getNumberLose());
        textLose.setId("text");
        textLose.setX(840);
        textLose.setY(50);

        root.getChildren().addAll(winView, textWin, loseView, textLose);


        ImageView infoDragon = new ImageView("dragon_drag.png");
        ImageView infoBalloon = new ImageView("balloon_drag.png");
        ImageView infoGoblin = new ImageView("goblin_drag.png");
        ImageView infoArcher = new ImageView("archer_drag.png");
        infoDragon.setX(920);
        infoDragon.setY(120);
        infoBalloon.setX(920);
        infoBalloon.setY(175);
        infoGoblin.setX(920);
        infoGoblin.setY(230);
        infoArcher.setX(920);
        infoArcher.setY(285);
        infoDragon.setFitWidth(45);
        infoDragon.setFitHeight(50);
        infoBalloon.setFitWidth(45);
        infoBalloon.setFitHeight(50);
        infoGoblin.setFitWidth(45);
        infoGoblin.setFitHeight(50);
        infoArcher.setFitWidth(45);
        infoArcher.setFitHeight(50);

        root.getChildren().addAll(infoDragon, infoBalloon, infoGoblin, infoArcher);

        ImageView infoPage = new ImageView();
        infoPage.setX(500);
        infoPage.setY(120);
        infoPage.setFitWidth(400);
        infoPage.setFitHeight(269);

        Text infoText = new Text();
        infoText.setId("textInfo");
        infoText.setX(720);
        infoText.setY(165);

        ImageView exitInfo = new ImageView();
        exitInfo.setX(873.6);
        exitInfo.setY(120.8);
        exitInfo.setFitWidth(25);
        exitInfo.setFitHeight(23);
        root.getChildren().addAll(infoPage, infoText, exitInfo);

        infoDragon.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mediaPlayerClick.stop();
            infoPage.setImage(new Image("dragon_info.png"));
            infoText.setText(new Dragon(0, 0).toString());
            exitInfo.setImage(new Image("exit_info.png"));
        });
        infoBalloon.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            infoPage.setImage(new Image("balloon_info.png"));
            infoText.setText(new DefBalloon(0, 0).toString());
            exitInfo.setImage(new Image("exit_info.png"));
            mediaPlayerClick.stop();
        });
        infoGoblin.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            infoPage.setImage(new Image("goblin_info.png"));
            infoText.setText(new GoblinBalloon(0, 0).toString());
            exitInfo.setImage(new Image("exit_info.png"));
            mediaPlayerClick.stop();
        });
        infoArcher.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            infoPage.setImage(new Image("archer_info.png"));
            infoText.setText(new ArcherBalloon(0, 0).toString());
            exitInfo.setImage(new Image("exit_info.png"));
            mediaPlayerClick.stop();
        });
        exitInfo.setOnMouseClicked(mouseEvent -> {
            mediaPlayerClick.play();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            infoPage.setImage(null);
            infoText.setText(null);
            exitInfo.setImage(null);
            mediaPlayerClick.stop();
        });


        return root;
    }
}
