package com.example.coc;

import data.LoadPlayerData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import model.Map.Map1;
import view.*;


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