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
import model.hero.Dragon;

import java.io.File;


public class DragonAttack extends Thread {
    public DragonAttack(double x, double y, AnchorPane root, Map map) {
        this.root = root;
        this.dragon = new Dragon(x,y);
        this.viewDragonL = new MyImageView(dragon.getImageViews().get(0));
        this.viewDragonAttackL = new MyImageView(dragon.getImageViews().get(1));
        this.viewDragonR = new MyImageView(dragon.getImageViews().get(2));
        this.viewDragonAttackR = new MyImageView(dragon.getImageViews().get(3));
        this.viewDragon = new MyImageView(viewDragonL);
        this.map = map;
        dragon.setViewHero(viewDragon);
        this.map.getAttackingHeroes().add(dragon);
        this.isDied = false;
        mediaPlayerAttack.setVolume(0.2);
    }
    private final AnchorPane root;
    private final ImageView viewDragonL;
    private final ImageView viewDragonR;
    private final ImageView viewDragonAttackL;
    private final ImageView viewDragonAttackR;
    private final ImageView viewDragon ;
    private final Dragon dragon;
    private final Map map;
    private boolean isDied;
    private final File audioFileAttack = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\fire.mp3");
    private final String audioFilePathAttack = audioFileAttack.toURI().toString();
    private final MediaPlayer mediaPlayerAttack = new MediaPlayer(new Media(audioFilePathAttack));
    @Override
    public void run() {
        synchronized (this){
            File audioFile = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\fly.mp3");
            String audioFilePath = audioFile.toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(audioFilePath));
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(10000);
            mediaPlayer.setVolume(0.3);
            Platform.runLater(() -> {
                root.getChildren().addAll(new MediaView(mediaPlayer), new MediaView(mediaPlayerAttack));
                root.getChildren().add(viewDragon);
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!isDied){
                Building building = moveToward();
                if (isDied)
                    break;
                attack(building);
            }
            Platform.runLater(() -> {
                root.getChildren().remove(viewDragon);
                mediaPlayer.stop();
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.getAttackingHeroes().remove(dragon);
        }
    }
    private synchronized Building moveToward(){
        double widthLowe = 10000;
        double width;
        Building building = null;
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                width = Math.sqrt((Math.pow(viewDragon.getX()-((Building) node).getImageView().getX(),2))+Math.pow(viewDragon.getY()-((Building) node).getImageView().getY(),2));
                if(width < widthLowe){
                    widthLowe = width;
                    building = (Building) node;
                }
            }
        }
        if (building != null){
            Building finalBuilding = building;
            Platform.runLater(() -> {
                if (viewDragon.getX() < finalBuilding.getImageView().getX()){
                    viewDragon.setImage(viewDragonL.getImage());
                    viewDragon.setFitWidth(viewDragonL.getFitWidth());
                    viewDragon.setFitHeight(viewDragonL.getFitHeight());
                } else {
                    viewDragon.setImage(viewDragonR.getImage());
                    viewDragon.setFitWidth(viewDragonR.getFitWidth());
                    viewDragon.setFitHeight(viewDragonR.getFitHeight());
                }
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            MoveTo moveTo = new MoveTo(viewDragon.getX(), viewDragon.getY());
            LineTo lineTo;
            if (viewDragon.getImage().equals(viewDragonL.getImage())){
                lineTo = new LineTo(building.getImageView().getX(), building.getImageView().getY());
            } else {
                lineTo = new LineTo(building.getImageView().getX()+building.getImageView().getFitWidth(), building.getImageView().getY());
            }
            Path path = new Path();
            path.getElements().addAll(moveTo, lineTo);
            PathTransition transition = new PathTransition();
            transition.setDuration(Duration.millis((widthLowe/dragon.getMovementSpeed())*400));
            transition.setCycleCount(1);
            transition.setNode(viewDragon);
            transition.setAutoReverse(false);
            transition.setPath(path);
            Platform.runLater(transition::play);
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            while (elapsedTime < (long) ((widthLowe/dragon.getMovementSpeed())*300)) {
                if (dragon.getHitPoints() <= 0){
                    isDied = true;
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
            if (viewDragon.getImage().equals(viewDragonR.getImage())){
                viewDragon.setImage(viewDragonAttackR.getImage());
                viewDragon.setFitWidth(viewDragonAttackR.getFitWidth());
                viewDragon.setFitHeight(viewDragonAttackR.getFitHeight());
            } else {
                viewDragon.setImage(viewDragonAttackL.getImage());
                viewDragon.setFitWidth(viewDragonAttackL.getFitWidth());
                viewDragon.setFitHeight(viewDragonAttackL.getFitHeight());
            }
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (building.getHitPoints() >= 0){
            if (dragon.getHitPoints() <= 0){
                isDied = true;
                break;
            }
            Platform.runLater(mediaPlayerAttack::play);
            building.setHitPoints((int)(building.getHitPoints()-(dragon.getDamagePerSecond()*1.25)));
            try {
                wait(1250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(mediaPlayerAttack::stop);
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
                if (viewDragon.getImage().equals(viewDragonAttackR.getImage())) {
                    viewDragon.setX(building.getImageView().getX() + building.getImageView().getFitWidth());
                    viewDragon.setY(building.getImageView().getY());
                } else {
                    viewDragon.setX(building.getImageView().getX());
                    viewDragon.setY(building.getImageView().getY());
                }
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
