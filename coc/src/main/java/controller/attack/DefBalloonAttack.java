package controller.attack;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Map.Map;
import model.MyImageView;
import model.building.Building;
import model.hero.DefBalloon;

import java.io.File;

public class DefBalloonAttack extends Thread {
    public DefBalloonAttack(double x, double y, AnchorPane root, Map map) {
        this.root = root;
        this.defBalloon = new DefBalloon(x,y);
        this.viewBalloonMove = new MyImageView(defBalloon.getImageViews().get(0));
        this.viewBalloonAttack = new MyImageView(defBalloon.getImageViews().get(1));
        this.viewBalloon = new MyImageView(viewBalloonMove);
        this.map = map;
        defBalloon.setViewHero(viewBalloon);
        this.map.getAttackingHeroes().add(defBalloon);
        this.isDied = false;
    }
    private final AnchorPane root;
    private final ImageView viewBalloonAttack;
    private final ImageView viewBalloonMove;
    private final ImageView viewBalloon;
    private final DefBalloon defBalloon;
    private final Map map;
    private boolean isDied;

    @Override
    public void run() {
        synchronized (this){
            File audioFile = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\balloonDeploy.mp3");
            String audioFilePath = audioFile.toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(audioFilePath));
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setVolume(0.5);
            Platform.runLater(() -> {
                root.getChildren().add(new MediaView(mediaPlayer));
                root.getChildren().add(viewBalloon);
            });
            while (!isDied){
                Building building = moveToward();
                if (isDied)
                    break;
                attack(building);
            }
            File audioFileDie = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\balloonDie.mp3");
            String audioFilePathDie = audioFileDie.toURI().toString();
            MediaPlayer mediaPlayerDie = new MediaPlayer(new Media(audioFilePathDie));
            mediaPlayerDie.setAutoPlay(true);
            mediaPlayerDie.setVolume(0.5);
            Platform.runLater(() -> {
                root.getChildren().remove(viewBalloon);
                root.getChildren().add(new MediaView(mediaPlayerDie));
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.getAttackingHeroes().remove(defBalloon);
        }
    }
    private synchronized Building moveToward(){
        double widthLowe = 10000;
        double width;
        Building building = null;
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                if (((Building) node).getBuildingType().equals(defBalloon.getFavoriteTarget())){
                    width = Math.sqrt((Math.pow(viewBalloon.getX()-((Building) node).getImageView().getX(),2))+Math.pow(viewBalloon.getY()-((Building) node).getImageView().getY(),2));
                    if(width < widthLowe){
                        widthLowe = width;
                        building = (Building) node;
                    }
                }
            }
        }
        if (building == null) {
            for (Node node : map.getBuildingsMap()){
                if (node instanceof Building){
                    width = Math.sqrt((Math.pow(viewBalloon.getX()-((Building) node).getImageView().getX(),2))+Math.pow(viewBalloon.getY()-((Building) node).getImageView().getY(),2));
                    if(width < widthLowe){
                        widthLowe = width;
                        building = (Building) node;
                    }
                }
            }
        }

        if (building != null){
            Platform.runLater(() -> {
                viewBalloon.setImage(viewBalloonMove.getImage());
                viewBalloon.setFitWidth(viewBalloonMove.getFitWidth());
                viewBalloon.setFitHeight(viewBalloonMove.getFitHeight());
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            MoveTo moveTo = new MoveTo(viewBalloon.getX(), viewBalloon.getY());
            LineTo lineTo = new LineTo(building.getImageView().getX()+(building.getImageView().getFitWidth()/2),building.getImageView().getY());
            Path path = new Path();
            path.getElements().addAll(moveTo, lineTo);
            PathTransition transition = new PathTransition();
            transition.setDuration(Duration.millis((widthLowe/defBalloon.getMovementSpeed())*400));
            transition.setCycleCount(1);
            transition.setNode(viewBalloon);
            transition.setAutoReverse(false);
            transition.setPath(path);
            Platform.runLater(transition::play);
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            while (elapsedTime < (long) ((widthLowe/defBalloon.getMovementSpeed())*300)) {
                if (defBalloon.getHitPoints() <= 0){
                    isDied = true;
                    Platform.runLater(() -> root.getChildren().remove(viewBalloon));
                    break;
                }
                try {
                    wait(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        }
        else {
            isDied = true;
        }
        return building;
    }
    private synchronized void attack(Building building){
        Platform.runLater(() -> {
            viewBalloon.setImage(viewBalloonAttack.getImage());
            viewBalloon.setFitWidth(viewBalloonAttack.getFitWidth());
            viewBalloon.setFitHeight(viewBalloonAttack.getFitHeight());
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (building.getHitPoints() >= 0){
            if (defBalloon.getHitPoints() <= 0){
                isDied = true;
                Platform.runLater(() -> root.getChildren().remove(viewBalloon));
                break;
            }
            building.setHitPoints(building.getHitPoints()-defBalloon.getDamagePerSecond());
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!isDied){
            map.getBuildingsMap().remove(building);
            Platform.runLater(() -> {
                root.getChildren().remove(building.getImageView());
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                viewBalloon.setX(building.getImageView().getX()+(building.getImageView().getFitWidth()/2));
                viewBalloon.setY(building.getImageView().getY());
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void myNotify(){
        notify();
    }
}
