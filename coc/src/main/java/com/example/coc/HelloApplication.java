package com.example.coc;

import data.LoadPlayerData;
import javafx.application.Application;
import javafx.stage.Stage;

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