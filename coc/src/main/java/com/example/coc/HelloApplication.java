package com.example.coc;

import model.Maps;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws InterruptedException {
//        new SignUp().start(new Stage());
//        AnchorPane root = new AnchorPane();
//        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
//        root.getChildren().add(new ArcherTower(300, 300).getImageView());
//        root.getChildren().add(new ArcherTower(400, 300).getImageView());
//        root.getChildren().add(new ArcherTower(500, 300).getImageView());
//        root.getChildren().add(new ArcherTower(600, 300).getImageView());
//        root.getChildren().add(new InfernoTower(370, 400).getImageView());
//        root.getChildren().add(new Tesla(400, 400).getImageView());
//        root.getChildren().add(new InfernoTower(370, 500).getImageView());
//        root.getChildren().add(new Tesla(400, 500).getImageView());
//        root.getChildren().add(new InfernoTower(470, 500).getImageView());
//        root.getChildren().add(new Tesla(500, 400).getImageView());
//        root.getChildren().add(new InfernoTower(370, 500).getImageView());
//        root.getChildren().add(new Tesla(400, 400).getImageView());
//        root.getChildren().add(new ArcherTower(500, 500).getImageView());
//        root.getChildren().add(new ArcherTower(600, 350).getImageView());
        stage.setScene(new Scene(Maps.map1(), 1000 ,737));
        stage.show();

        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}