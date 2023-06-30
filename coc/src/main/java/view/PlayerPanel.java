package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Map.Map;
import model.Map.Map4;
import model.Player;
import model.hero.GoblinBalloon;

import java.util.ArrayList;

public class PlayerPanel extends Application {
    public PlayerPanel(Player player, ArrayList<Player> players) {
        this.player = player;
        this.players = players;
    }

    private final Player player;
    private final ArrayList<Player> players;
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root(stage), 1000, 737);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();
    }

    AnchorPane root(Stage stage){
        AnchorPane root = player.getMap().getMapView();

        ImageView attackView = new ImageView("attack.png");
        attackView.setX(20);
        attackView.setY(620);
        attackView.setFitWidth(100);
        attackView.setFitHeight(100);
        attackView.setOnMouseClicked(mouseEvent -> {
            new SearchOpponents(player, players).start(new Stage());
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

        return root;
    }
}
