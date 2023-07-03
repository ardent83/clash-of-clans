package controller.attack;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Map.Map;
import model.MyImageView;
import model.building.Building;
import model.hero.Dragon;


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
        Platform.runLater(() -> root.getChildren().add(viewDragon));
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
    @Override
    public void run() {
        synchronized (this){
            while (!isDied){
                Building building = moveToward();
                if (isDied)
                    break;
                attack(building);
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
            transition.play();
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            while (elapsedTime < (long) ((widthLowe/dragon.getMovementSpeed())*300)) {
                if (dragon.getHitPoints() <= 0){
                    isDied = true;
                    Platform.runLater(() -> root.getChildren().remove(viewDragon));
                    break;
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
                Platform.runLater(() -> root.getChildren().remove(viewDragon));
                break;
            }
            building.setHitPoints(building.getHitPoints()-dragon.getDamagePerSecond());
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
