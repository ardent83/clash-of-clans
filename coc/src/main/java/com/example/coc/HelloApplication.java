package com.example.coc;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Maps;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.building.*;
import model.hero.*;
import view.SignUp;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws InterruptedException {
        new SignUp().start(new Stage());
//        AnchorPane root = new AnchorPane();
//        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        AnchorPane map = Maps.getMap4();
        Dragon dragon = new Dragon(100, 100);
        DefBalloon defBalloon =  new DefBalloon(200, 100);
        ImageView imageView = dragon.getImageViews().get(0);
        ImageView imageView1 = defBalloon.getImageViews().get(0);
        map.getChildren().add(imageView1);
        map.getChildren().add(imageView);
        map.getChildren().add(new ArcherBalloon(300, 100).getImageViews().get(0));
        map.getChildren().add(new GoblinBalloon(450, 100).getImageViews().get(0));
        stage.setScene(new Scene(map, 1000, 737));
        MoveTo moveTo = new MoveTo(imageView.getX(), imageView.getY());
        ImageView node = (ImageView) map.getChildren().get(2);
        LineTo lineTo = new LineTo(node.getX(), (node.getY()));
        double width = Math.sqrt((Math.pow(dragon.getImageViews().get(0).getX()-node.getX(),2))+Math.pow(dragon.getImageViews().get(0).getY()-node.getY(),2));
        Path path = new Path();
        path.getElements().addAll(moveTo, lineTo);
        PathTransition transition = new PathTransition();
        transition.setNode(imageView1);
        System.out.println(width/dragon.getMovementSpeed());
        transition.setDuration(Duration.millis((width/dragon.getMovementSpeed())*400));
        transition.setCycleCount(10);
        transition.setAutoReverse(false);
        transition.setPath(path);

        transition.play();
        stage.show();
        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}