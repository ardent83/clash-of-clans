package controller.defense;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Map.Map;
import model.building.ArcherTower;
import model.hero.Dragon;
import model.hero.Hero;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ArcherTowerDefense extends Thread {
    public ArcherTowerDefense(AnchorPane root, Map map, ArcherTower archerTower, AtomicInteger capacityInt) {
        this.isDestroyed = false;
        this.root = root;
        this.map = map;
        this.archerTower = archerTower;
        this.capacityInt = capacityInt;
    }

    private boolean isDestroyed;
    private final AnchorPane root;
    private final Map map;
    private final ArcherTower archerTower;
    private final AtomicInteger capacityInt;
    @Override
    public synchronized void run() {
        while (!isDestroyed) {
            Hero hero = selectHero();
            if (isDestroyed)
                break;
            if (hero != null)
                attack(hero);
        }
    }
    private synchronized Hero selectHero(){
        double width;
        Hero hero = null;
        for (Hero attackingHero : new ArrayList<>(map.getAttackingHeroes())) {
            if (root.getChildren().contains(attackingHero.getViewHero())){
                width = Math.sqrt((Math.pow(archerTower.getImageView().getX()+48 - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterX(), 2))
                        + Math.pow(archerTower.getImageView().getY()+20 - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterY(), 2));
                if (width < archerTower.getRange()) {
                    hero = attackingHero;
                }
            }
        }
        if (map.getBuildingsMap().size() == 0 || archerTower.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
            isDestroyed = true;
        }
        if (!isDestroyed && hero == null){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return hero;
    }
    private synchronized void attack(Hero hero){
        MoveTo moveTo = new MoveTo(archerTower.getImageView().getX()+48, archerTower.getImageView().getY()+20);
        LineTo lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(), hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
        Circle circle = new Circle(archerTower.getImageView().getX()+48, archerTower.getImageView().getY()+20, 3);
        while (hero.getHitPoints() >= 0 && Math.sqrt(Math.pow(moveTo.getX()-lineTo.getX(), 2)+ Math.pow(moveTo.getY()-lineTo.getY(), 2)) < archerTower.getRange()) {
            if (archerTower.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
                isDestroyed = true;
                break;
            }
            circle = new Circle(circle.getCenterX(), circle.getCenterY(), circle.getRadius());
            Circle finalCircle = circle;
            Platform.runLater(() -> {
                root.getChildren().add(finalCircle);
                myNotify();
            });
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (root.getChildren().contains(hero.getViewHero())) {
                lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(), hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
                Path path = new Path();
                path.getElements().addAll(new MoveTo(moveTo.getX(), moveTo.getY()), lineTo);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(1000));
                pathTransition.setPath(path);
                pathTransition.setCycleCount(1);
                pathTransition.setNode(finalCircle);
                Platform.runLater(pathTransition::play);
                hero.setHitPoints(hero.getHitPoints() - (archerTower.getDamagePerSecond()));
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    root.getChildren().remove(finalCircle);
                    myNotify();
                });
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Circle finalCircle1 = circle;
        Platform.runLater(() -> {
            root.getChildren().remove(finalCircle1);
            myNotify();
        });
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void myNotify(){
        notify();
    }
}
