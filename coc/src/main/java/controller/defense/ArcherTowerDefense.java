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
            width = Math.sqrt((Math.pow(archerTower.getImageView().getX() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterX(), 2))
                    + Math.pow(archerTower.getImageView().getY() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterY(), 2));
            if (width < archerTower.getRange()) {
                hero = attackingHero;
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
        Path path = new Path();
        MoveTo moveTo = new MoveTo(archerTower.getImageView().getX()+48, archerTower.getImageView().getY()+20);
        Circle circle = new Circle(archerTower.getImageView().getX()+48, archerTower.getImageView().getY()+20, 3);
        while (hero.getHitPoints() >= 0){
            if (archerTower.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
                isDestroyed = true;
                break;
            }
            Platform.runLater(() -> root.getChildren().add(circle));
            LineTo lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(),hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
            if (Math.sqrt(Math.pow(moveTo.getX()-lineTo.getX(), 2)+ Math.pow(moveTo.getY()-lineTo.getY(), 2)) < archerTower.getRange()){
                path.getElements().clear();
                path.getElements().addAll(moveTo, lineTo);
                PathTransition pathTransition = new PathTransition(Duration.millis(1000),path);
                pathTransition.setCycleCount(1);
                pathTransition.setNode(circle);
                pathTransition.play();
                hero.setHitPoints(hero.getHitPoints() - (archerTower.getDamagePerSecond()));
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    root.getChildren().remove(circle);
                });
            } else {
                Platform.runLater(() -> {
                    root.getChildren().remove(circle);
                });
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Platform.runLater(() -> {
            root.getChildren().remove(circle);
        });
    }
}
