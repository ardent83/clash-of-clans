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
    }

    private boolean isDestroyed;
    private final AnchorPane root;
    private final Map map;
    private final Tesla tesla;
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
                width = Math.sqrt((Math.pow(tesla.getImageView().getX() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterX(), 2))
                        + Math.pow(tesla.getImageView().getY() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getCenterY(), 2));
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
        Path path = new Path();
        MoveTo moveTo = new MoveTo(tesla.getImageView().getX()+37, tesla.getImageView().getY()+18);
        ImageView circleImpact = new ImageView("tesla_attack.gif");
        circleImpact.setFitHeight(25);
        circleImpact.setFitWidth(42.5);
        while (hero.getHitPoints() >= 0){
            if (tesla.getHitPoints() <= 0 || (map.getAttackingHeroes().size() == 0 && capacityInt.get() == 0)) {
                isDestroyed = true;
                break;
            }
            Platform.runLater(() -> root.getChildren().add(circleImpact));
            if (root.getChildren().contains(hero.getViewHero())){
                LineTo lineTo = new LineTo(hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterX(),hero.getViewHero().localToScene(hero.getViewHero().getLayoutBounds()).getCenterY());
                if (Math.sqrt(Math.pow(moveTo.getX()-lineTo.getX(), 2)+ Math.pow(moveTo.getY()-lineTo.getY(), 2)) < tesla.getRange()){
                    path.getElements().clear();
                    path.getElements().addAll(moveTo, lineTo);
                    PathTransition pathTransition = new PathTransition(Duration.millis(1000),path);
                    pathTransition.setCycleCount(1);
                    pathTransition.setNode(circleImpact);
                    pathTransition.play();
                    hero.setHitPoints(hero.getHitPoints() - (tesla.getDamagePerSecond()));
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        root.getChildren().remove(circleImpact);
                    });
                } else {
                    Platform.runLater(() -> {
                        root.getChildren().remove(circleImpact);
                    });
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Platform.runLater(() -> {
            root.getChildren().remove(circleImpact);
        });
    }
}
