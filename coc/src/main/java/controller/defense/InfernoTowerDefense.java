package controller.defense;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import model.Map.Map;
import model.building.ArcherTower;
import model.building.InfernoTower;
import model.hero.Hero;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class InfernoTowerDefense extends Thread {
    public InfernoTowerDefense(AnchorPane root, Map map, InfernoTower infernoTower, AtomicInteger capacityInt) {
        this.isDestroyed = false;
        this.root = root;
        this.map = map;
        this.infernoTower = infernoTower;
        this.capacityInt = capacityInt;
    }

    private boolean isDestroyed;
    private final AnchorPane root;
    private final Map map;
    private final InfernoTower infernoTower;
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
                width = Math.sqrt((Math.pow(infernoTower.getImageView().getX() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterX(), 2))
                        + Math.pow(infernoTower.getImageView().getY() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterY(), 2));
                if (width < infernoTower.getRange()) {
                    hero = attackingHero;
                }
            }
        }
        if (map.getBuildingsMap().size() == 0 || infernoTower.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
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
        path.setStroke(Color.web("#FF6F00"));
        path.setStrokeWidth(3);
        MoveTo moveTo = new MoveTo(infernoTower.getImageView().getX()+26.4, infernoTower.getImageView().getY()+13.6);
//        LineTo lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(),hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
//        path.getElements().addAll(moveTo, lineTo);
        Platform.runLater(() -> {
            root.getChildren().add(path);
        });
        while (hero.getHitPoints() >= 0){
            if (infernoTower.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
                isDestroyed = true;
                break;
            }
            if (root.getChildren().contains(hero.getViewHero())){
                LineTo lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(),hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
                if (Math.sqrt(Math.pow(moveTo.getX()-lineTo.getX(), 2)+ Math.pow(moveTo.getY()-lineTo.getY(), 2)) < infernoTower.getRange()){
                    path.getElements().clear();
                    path.getElements().addAll(moveTo, lineTo);
                    Platform.runLater(() -> {
                        root.getChildren().remove(path);
                        root.getChildren().add(path);
                    });
                    hero.setHitPoints(hero.getHitPoints() - (infernoTower.getDamagePerSecond()));
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.runLater(() -> {
                        root.getChildren().remove(path);
                    });
                    try {
                        wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Platform.runLater(() -> {
            root.getChildren().remove(path);
        });
    }
    public synchronized void myNotify(){
        notify();
    }
    public synchronized void myWait(){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void myWait(long timeMillis){
        try {
            wait(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
