package com.example.coc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import model.building.ArcherTower;
import model.building.InfernoTower;
import model.building.Tesla;
import view.SignUp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
//        new SignUp().start(new Stage());
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        root.getChildren().add(new ArcherTower(300, 300).getImageView());
        root.getChildren().add(new ArcherTower(400, 300).getImageView());
        root.getChildren().add(new ArcherTower(500, 300).getImageView());
        root.getChildren().add(new ArcherTower(600, 300).getImageView());
        root.getChildren().add(new InfernoTower(370, 400).getImageView());
        root.getChildren().add(new Tesla(400, 400).getImageView());
        root.getChildren().add(new InfernoTower(370, 500).getImageView());
        root.getChildren().add(new Tesla(400, 500).getImageView());
        root.getChildren().add(new InfernoTower(470, 500).getImageView());
        root.getChildren().add(new Tesla(500, 400).getImageView());
        root.getChildren().add(new InfernoTower(370, 500).getImageView());
        root.getChildren().add(new Tesla(400, 400).getImageView());
        root.getChildren().add(new ArcherTower(500, 500).getImageView());
        root.getChildren().add(new ArcherTower(600, 350).getImageView());
        stage.setScene(new Scene(root, 1000 ,737));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}