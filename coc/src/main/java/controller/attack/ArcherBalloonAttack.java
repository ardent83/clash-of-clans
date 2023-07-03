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
import model.hero.ArcherBalloon;
import model.hero.GoblinBalloon;

public class ArcherBalloonAttack extends Thread {
    public ArcherBalloonAttack(double x, double y, AnchorPane root, Map map) {
        this.root = root;
        this.archerBalloon = new ArcherBalloon(x,y);
        this.viewBalloon = new MyImageView(archerBalloon.getImageViews().get(0));
        this.map = map;
        archerBalloon.setViewHero(viewBalloon);
        this.map.getAttackingHeroes().add(archerBalloon);
        this.isDied = false;
        Platform.runLater(() -> root.getChildren().add(viewBalloon));
    }
    private final AnchorPane root;
    private final ImageView viewBalloon;
    private final ArcherBalloon archerBalloon;
    private final Map map;
    private boolean isDied;
    private double widthLowe;
    @Override
    public void run() {
        synchronized (this){
            while (!isDied){
                Building building = moveToward();
                if (isDied)
                    break;
                attack(building);
            }
            map.getAttackingHeroes().remove(archerBalloon);
        }
    }
    private synchronized Building moveToward(){
        widthLowe = 10000;
        double width;
        Building building = null;
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                width = Math.sqrt((Math.pow(viewBalloon.getX() - (((Building) node).getImageView().getX()+(((Building) node).getImageView().getFitWidth()/2)), 2)) + Math.pow(viewBalloon.getY() - (((Building) node).getImageView().getY()+(((Building) node).getImageView().getFitHeight()/2)), 2));
                width -= archerBalloon.getRang();
                if (width < widthLowe) {
                    widthLowe = width;
                    building = (Building) node;
                }
            }
        }
        if (widthLowe <= 0 && building != null)
            return building;

        if (building != null){
            MoveTo moveTo = new MoveTo(viewBalloon.getX(), viewBalloon.getY());
            LineTo lineTo = new LineTo((building.getImageView().getX()+(building.getImageView().getFitWidth()/2)), (building.getImageView().getY()+(building.getImageView().getFitHeight()/2)));
            Path path = new Path();
            path.getElements().addAll(moveTo, lineTo);
            PathTransition transition = new PathTransition();
            transition.setDuration(Duration.millis((widthLowe/archerBalloon.getMovementSpeed())*400));
            transition.setCycleCount(1);
            transition.setNode(viewBalloon);
            transition.setAutoReverse(false);
            transition.setPath(path);
            transition.play();
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            while (elapsedTime < (long) ((widthLowe/archerBalloon.getMovementSpeed())*300)) {
                if (archerBalloon.getHitPoints() <= 0){
                    isDied = true;
                    Platform.runLater(() -> root.getChildren().remove(viewBalloon));
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
        while (building.getHitPoints() >= 0){
            if (archerBalloon.getHitPoints() <= 0){
                isDied = true;
                Platform.runLater(() -> root.getChildren().remove(viewBalloon));
                break;
            }
            building.setHitPoints(building.getHitPoints()-archerBalloon.getDamagePerSecond());
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
                if (widthLowe > 0){
                    viewBalloon.setX(building.getImageView().getX()+(building.getImageView().getFitWidth()/2));
                    viewBalloon.setY(building.getImageView().getY()+(building.getImageView().getFitHeight()/2));
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
