package com.example.coc;

import data.LoadPlayerData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import view.*;

import java.io.File;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws InterruptedException {
        LoadPlayerData loadPlayerData = new LoadPlayerData();
        loadPlayerData.start();
        loadPlayerData.join();
        new SignUp(loadPlayerData.getPlayers()).start(new Stage());
    }

    public static void main(String[] args) {
        launch();
    }
}