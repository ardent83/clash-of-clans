package com.example.coc;

import data.LoadPlayerData;
import data.SavePlayerData;
import data.UpdatePlayerData;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Map.Map4;
import model.Player;
import model.hero.*;
import view.LogIn;
import view.SignUp;

import java.util.ArrayList;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws InterruptedException {
        LoadPlayerData loadPlayerData = new LoadPlayerData();
        loadPlayerData.start();
        loadPlayerData.join();

//        __________________________________________________________________
        new LogIn(loadPlayerData.getPlayers()).start(new Stage());
//        new SignUp(new ArrayList<>()).start(new Stage());
//        AnchorPane root = new AnchorPane();
//        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
//        Player player = new Player("mmhlpv5", "1234", new Map4());
//        AnchorPane map = player.getMap().getMapView();
//        new SavePlayerData(player).start();
//        LoadPlayerData loadPlayerData = new LoadPlayerData();
//        loadPlayerData.start();
//        loadPlayerData.join();
//        for (Player player1 : loadPlayerData.getPlayers()){
//            new UpdatePlayerData(player1).start();
//        }
//        Dragon dragon = new Dragon(100, 100);
//        DefBalloon defBalloon =  new DefBalloon(200, 100);
//        ImageView imageView = dragon.getImageViews().get(0);
//        ImageView imageView1 = defBalloon.getImageViews().get(0);
//        map.getChildren().add(new GoblinBalloon(450, 100).getImageViews().get(0));
//        map.getChildren().add(new ArcherBalloon(300, 100).getImageViews().get(0));
//        map.getChildren().add(imageView1);
//        map.getChildren().add(imageView);
//
//
//        stage.setScene(new Scene(map, 1000, 737));
//        MoveTo moveTo = new MoveTo(imageView.getX(), imageView.getY());
//        ImageView node = (ImageView) map.getChildren().get(2);
//        LineTo lineTo = new LineTo(100, 700);
//        double width = Math.sqrt((Math.pow(dragon.getImageViews().get(0).getX()-node.getX(),2))+Math.pow(dragon.getImageViews().get(0).getY()-node.getY(),2));
//        Path path = new Path();
//        path.getElements().addAll(moveTo, lineTo);
//        PathTransition transition = new PathTransition();
//        transition.setNode(imageView);
//        System.out.println(width/dragon.getMovementSpeed());
//        transition.setDuration(Duration.millis((width/dragon.getMovementSpeed())*400));
//        transition.setCycleCount(10);
//        transition.setAutoReverse(false);
//        transition.setPath(path);
//
//        transition.play();
//        stage.show();
//        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}