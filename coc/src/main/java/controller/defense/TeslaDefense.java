package controller.defense;


import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Map.Map;
import model.MyImageView;
import model.building.Tesla;
import model.hero.Hero;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TeslaDefense extends Thread {
    public TeslaDefense(AnchorPane root, Map map, Tesla tesla, AtomicInteger capacityInt) {
        this.isDestroyed = false;
        this.root = root;
        this.map = map;
        this.tesla = tesla;
        this.capacityInt = capacityInt;
        circleImpact = new ImageView("tesla_attack.gif");
        circleImpact.setFitHeight(25);
        circleImpact.setFitWidth(42.5);
    }

    private boolean isDestroyed;
    private final AnchorPane root;
    private final Map map;
    private final Tesla tesla;
    private final AtomicInteger capacityInt;
     private final ImageView circleImpact;
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
                width = Math.sqrt((Math.pow(tesla.getImageView().getX()+37 - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterX(), 2))
                        + Math.pow(tesla.getImageView().getY()+18 - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterY(), 2));
                if (width < tesla.getRange()) {
                    hero = attackingHero;
                }
            }
        }
        if (map.getBuildingsMap().size() == 0 || tesla.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
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
        MoveTo moveTo = new MoveTo(tesla.getImageView().getX()+37, tesla.getImageView().getY()+18);
        LineTo lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(),hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
        while (hero.getHitPoints() >= 0 && Math.sqrt(Math.pow(moveTo.getX()-lineTo.getX(), 2)+ Math.pow(moveTo.getY()-lineTo.getY(), 2)) < tesla.getRange()){
            if (tesla.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
                isDestroyed = true;
                break;
            }
            ImageView finalCircleImpact = circleImpact;
            Platform.runLater(() -> {
                root.getChildren().add(finalCircleImpact);
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
                pathTransition.setDuration(Duration.millis(80));
                pathTransition.setPath(path);
                pathTransition.setCycleCount(1);
                pathTransition.setNode(finalCircleImpact);
                Platform.runLater(pathTransition::play);
                hero.setHitPoints(hero.getHitPoints() - (tesla.getDamagePerSecond() * 2));
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    root.getChildren().remove(finalCircleImpact);
                    myNotify();
                });
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ImageView finalCircleImpact1 = circleImpact;
        Platform.runLater(() -> {
            root.getChildren().remove(finalCircleImpact1);
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
