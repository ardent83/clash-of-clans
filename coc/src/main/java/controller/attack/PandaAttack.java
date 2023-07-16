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
import model.hero.Panda;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;


public class PandaAttack extends Thread {
    public PandaAttack(double x, double y, AnchorPane root, Map map) {
        this.root = root;
        this.panda = new Panda(x,y);
        this.viewPandaL = new MyImageView(panda.getImageViews().get(0));
        this.viewPandaAttackL = new MyImageView(panda.getImageViews().get(1));
        this.viewPandaR = new MyImageView(panda.getImageViews().get(2));
        this.viewPandaAttackR = new MyImageView(panda.getImageViews().get(3));
        this.viewPanda = new MyImageView(viewPandaL);
        this.map = map;
        panda.setViewHero(viewPanda);
        this.map.getAttackingHeroes().add(panda);
        root.getChildren().add(new MediaView(mediaPlayerMove));
        this.isDied = false;
        mediaPlayerMove.setVolume(0.2);
    }
    private final AnchorPane root;
    private final ImageView viewPandaL;
    private final ImageView viewPandaR;
    private final ImageView viewPandaAttackL;
    private final ImageView viewPandaAttackR;
    private final ImageView viewPanda ;
    private final Panda panda;
    private final Map map;
    File audioFileMove = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\not_path.mp3");
    String audioFilePathMove = audioFileMove.toURI().toString();
    MediaPlayer mediaPlayerMove = new MediaPlayer(new Media(audioFilePathMove));
    private final AtomicInteger count = new AtomicInteger(0);
    private boolean isDied;
    @Override
    public void run() {
        synchronized (this){
            File audioFile = new File("D:\\javacode\\final-project-game-ardent\\coc\\src\\main\\resources\\enter_panda.mp3");
            String audioFilePath = audioFile.toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(audioFilePath));
            mediaPlayer.setAutoPlay(true);
            Platform.runLater(() -> {
                root.getChildren().add(new MediaView(mediaPlayer));
                root.getChildren().add(viewPanda);
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
                root.getChildren().remove(viewPanda);
                mediaPlayer.stop();
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.getAttackingHeroes().remove(panda);
        }
    }
    private synchronized Building moveToward(){
        double widthLowe = 10000;
        double width;
        Building building = null;
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                width = Math.sqrt((Math.pow(viewPanda.getX()-((Building) node).getImageView().getX(),2))+Math.pow(viewPanda.getY()-((Building) node).getImageView().getY(),2));
                if(width < widthLowe){
                    widthLowe = width;
                    building = (Building) node;
                }
            }
        }
        if (building != null){
            Building finalBuilding = building;
            Platform.runLater(() -> {
                if (viewPanda.getX() < finalBuilding.getImageView().getX()){
                    viewPanda.setImage(viewPandaL.getImage());
                    viewPanda.setFitWidth(viewPandaL.getFitWidth());
                    viewPanda.setFitHeight(viewPandaL.getFitHeight());
                } else {
                    viewPanda.setImage(viewPandaR.getImage());
                    viewPanda.setFitWidth(viewPandaR.getFitWidth());
                    viewPanda.setFitHeight(viewPandaR.getFitHeight());
                }
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            MoveTo moveTo = new MoveTo(viewPanda.getX(), viewPanda.getY());
            LineTo lineTo;
            if (viewPanda.getImage().equals(viewPandaL.getImage())){
                lineTo = new LineTo(building.getImageView().getX(), building.getImageView().getY()+(building.getImageView().getFitHeight()/3));
            } else {
                lineTo = new LineTo(building.getImageView().getX()+building.getImageView().getFitWidth(), building.getImageView().getY()+(building.getImageView().getFitHeight()/3));
            }
            Platform.runLater(mediaPlayerMove::stop);
            Path path = new Path();
            if (count.getAndAdd(1)%3 == 1){
                Platform.runLater(mediaPlayerMove::play);
            }
            path.getElements().addAll(moveTo, lineTo);
            PathTransition transition = new PathTransition();
            transition.setDuration(Duration.millis((widthLowe/panda.getMovementSpeed())*400));
            transition.setCycleCount(1);
            transition.setNode(viewPanda);
            transition.setAutoReverse(false);
            transition.setPath(path);
            Platform.runLater(transition::play);
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            while (elapsedTime < (long) ((widthLowe/panda.getMovementSpeed())*300)) {
                if (panda.getHitPoints() <= 0){
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
            if (viewPanda.getImage().equals(viewPandaR.getImage())){
                viewPanda.setImage(viewPandaAttackR.getImage());
                viewPanda.setFitWidth(viewPandaAttackR.getFitWidth());
                viewPanda.setFitHeight(viewPandaAttackR.getFitHeight());
            } else {
                viewPanda.setImage(viewPandaAttackL.getImage());
                viewPanda.setFitWidth(viewPandaAttackL.getFitWidth());
                viewPanda.setFitHeight(viewPandaAttackL.getFitHeight());
            }
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (building.getHitPoints() >= 0){
            if (panda.getHitPoints() <= 0){
                isDied = true;
                break;
            }
//            Platform.runLater(mediaPlayer::play);
            building.setHitPoints((int)(building.getHitPoints()-(panda.getDamagePerSecond()*1.25)));
            try {
                wait(1250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Platform.runLater(mediaPlayer::stop);
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
                if (viewPanda.getImage().equals(viewPandaAttackR.getImage())) {
                    viewPanda.setX(building.getImageView().getX() + building.getImageView().getFitWidth());
                    viewPanda.setY(building.getImageView().getY());
                } else {
                    viewPanda.setX(building.getImageView().getX());
                    viewPanda.setY(building.getImageView().getY());
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

