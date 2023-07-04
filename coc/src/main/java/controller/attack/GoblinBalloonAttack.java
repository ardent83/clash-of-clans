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
import model.hero.GoblinBalloon;

public class GoblinBalloonAttack extends Thread {
    public GoblinBalloonAttack(double x, double y, AnchorPane root, Map map) {
        this.root = root;
        this.goblinBalloon = new GoblinBalloon(x,y);
        this.viewBalloonMove = new MyImageView(goblinBalloon.getImageViews().get(0));
        this.viewBalloonAttack = new MyImageView(goblinBalloon.getImageViews().get(1));
        this.viewBalloon = new MyImageView(viewBalloonMove);
        this.map = map;
        goblinBalloon.setViewHero(viewBalloon);
        this.map.getAttackingHeroes().add(goblinBalloon);
        this.isDied = false;
    }
    private final AnchorPane root;
    private final ImageView viewBalloonAttack;
    private final ImageView viewBalloonMove;
    private final ImageView viewBalloon;
    private final GoblinBalloon goblinBalloon;
    private final Map map;
    private boolean isDied;

    @Override
    public void run() {
        synchronized (this){
            Platform.runLater(() -> {
                root.getChildren().add(viewBalloon);
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
                root.getChildren().remove(viewBalloon);
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.getAttackingHeroes().remove(goblinBalloon);
        }
    }
    private synchronized Building moveToward(){
        double widthLowe = 10000;
        double width;
        Building building = null;
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                if (((Building) node).getBuildingType().equals(goblinBalloon.getFavoriteTarget())){
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
            transition.setDuration(Duration.millis((widthLowe/goblinBalloon.getMovementSpeed())*400));
            transition.setCycleCount(1);
            transition.setNode(viewBalloon);
            transition.setAutoReverse(false);
            transition.setPath(path);
            transition.play();
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            while (elapsedTime < (long) ((widthLowe/goblinBalloon.getMovementSpeed())*300)) {
                if (goblinBalloon.getHitPoints() <= 0){
                    isDied = true;
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
            if (goblinBalloon.getHitPoints() <= 0){
                isDied = true;
                break;
            }
            if (building.getBuildingType().equals(goblinBalloon.getFavoriteTarget())){
                building.setHitPoints(building.getHitPoints()-(goblinBalloon.getDamagePerSecond()*5));
            } else {
                building.setHitPoints(building.getHitPoints()-goblinBalloon.getDamagePerSecond());
            }
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
