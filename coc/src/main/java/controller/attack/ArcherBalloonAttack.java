package controller.attack;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Map.Map;
import model.MyImageView;
import model.building.Building;
import model.hero.ArcherBalloon;

public class ArcherBalloonAttack extends Thread {
    public ArcherBalloonAttack(double x, double y, AnchorPane root, Map map) {
        this.root = root;
        this.archerBalloon = new ArcherBalloon(x,y);
        this.viewBalloon = new MyImageView(archerBalloon.getImageViews().get(0));
        this.map = map;
        archerBalloon.setViewHero(viewBalloon);
        this.map.getAttackingHeroes().add(archerBalloon);
        this.isDied = false;
        this.nearestPointLineTo = new double[2];
    }
    private final AnchorPane root;
    private final ImageView viewBalloon;
    private final ArcherBalloon archerBalloon;
    private final Map map;
    private boolean isDied;
    private boolean insideRadius;
    private double widthLowe;
    double[] nearestPointLineTo;
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
            map.getAttackingHeroes().remove(archerBalloon);
        }
    }
    private synchronized Building moveToward(){
        widthLowe = 10000;
        double width;
        Building building = null;
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                width = distance(viewBalloon.getX()+(viewBalloon.getFitWidth()/2),viewBalloon.getY()+(viewBalloon.getFitHeight()/2),
                        ((Building) node).getImageView().getX()+(((Building) node).getImageView().getFitWidth()/2),
                        ((Building) node).getImageView().getY()+(((Building) node).getImageView().getFitHeight()/2));
                if(width < widthLowe){
                    widthLowe = width;
                    building = (Building) node;
                    insideRadius = true;
                }
            }
        }
        for (Node node : map.getBuildingsMap()){
            if (node instanceof Building){
                double[] nearestPoint = nearestPointOnCircle(((Building) node).getImageView().getX()+(((Building) node).getImageView().getFitWidth()/2),
                        ((Building) node).getImageView().getY()+(((Building) node).getImageView().getFitHeight()/2),
                        archerBalloon.getRang(), viewBalloon.getX()+(viewBalloon.getFitWidth()/2),
                        viewBalloon.getY()+(viewBalloon.getFitHeight()/2));
                width = distance(viewBalloon.getX()+(viewBalloon.getFitWidth()/2), viewBalloon.getY()+(viewBalloon.getFitHeight()/2),
                                    nearestPoint[0], nearestPoint[1]);

                if (width < widthLowe) {
                    widthLowe = width;
                    building = (Building) node;
                    nearestPointLineTo = nearestPoint;
                    insideRadius = false;
                }
            }
        }
        if (building != null && !insideRadius){
            MoveTo moveTo = new MoveTo(viewBalloon.getX(), viewBalloon.getY());
            LineTo lineTo = new LineTo(nearestPointLineTo[0], nearestPointLineTo[1]);
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
                    break;
                }
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        }
        else if (building == null){
            isDied = true;
        }
        return building;
    }
    private synchronized void attack(Building building){
        if (!insideRadius){
            Platform.runLater(() -> {
                viewBalloon.setX(nearestPointLineTo[0]);
                viewBalloon.setY(nearestPointLineTo[1]);
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Path path = new Path();
        MoveTo moveTo = new MoveTo(viewBalloon.getX(), viewBalloon.getY()+20);
        LineTo lineTo = new LineTo(building.getImageView().getX()+(building.getImageView().getFitWidth()/2),
                building.getImageView().getY()+20);
        Circle circle = new Circle(viewBalloon.getX()+28.8, viewBalloon.getY()+60,3);
        circle.setFill(Color.web("#B442F7"));
        path.getElements().addAll(moveTo, lineTo);
        Platform.runLater(() -> {
            root.getChildren().add(circle);
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (building.getHitPoints() >= 0){
            if (archerBalloon.getHitPoints() <= 0){
                isDied = true;
                break;
            }
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(1000));
            pathTransition.setPath(path);
            pathTransition.setCycleCount(1);
            pathTransition.setNode(circle);
            pathTransition.play();
            building.setHitPoints(building.getHitPoints()-archerBalloon.getDamagePerSecond());
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            root.getChildren().remove(circle);
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        }
    }

    public synchronized void myNotify(){
        notify();
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double[] nearestPointOnCircle(double cx, double cy, double r, double x, double y) {
        double distanceToCenter = distance(x, y, cx, cy);
        if (distanceToCenter < r) {
            return new double[]{x, y};
        } else {
            double m1 = (cy - y) / (cx - x);

            double intercept = y - m1 * x;

            double a = 1 + Math.pow(m1, 2);
            double b = -2 * cx + 2 * m1 * (intercept - cy);
            double c = Math.pow(cx, 2) + Math.pow(intercept - cy, 2) - Math.pow(r, 2);

            double x1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
            double x2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);

            double y1 = m1 * x1 + intercept;
            double y2 = m1 * x2 + intercept;

            double distance1 = distance(x, y, x1, y1);
            double distance2 = distance(x, y, x2, y2);

            if (distance1 < distance2) {
                return new double[]{x1, y1};
            } else {
                return new double[]{x2, y2};
            }
        }
    }

}
