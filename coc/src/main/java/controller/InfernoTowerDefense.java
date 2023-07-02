package controller;

import javafx.scene.layout.AnchorPane;
import model.Map.Map;
import model.building.InfernoTower;
import model.hero.Hero;

import java.util.ArrayList;


public class InfernoTowerDefense extends Thread {
    public InfernoTowerDefense(AnchorPane root, Map map, InfernoTower infernoTower) {
        this.isDestroyed = false;
        this.root = root;
        this.map = map;
        this.infernoTower = infernoTower;
        this.xCenter = (infernoTower.getImageView().getX() + ((infernoTower.getImageView().getFitWidth())/2));
        this.yCenter = (infernoTower.getImageView().getY() + ((infernoTower.getImageView().getFitHeight())/2));
    }

    private boolean isDestroyed;
    private final AnchorPane root;
    private final Map map;
    private final InfernoTower infernoTower;
    private final double xCenter;
    private final double yCenter;
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
            width = Math.sqrt((Math.pow(infernoTower.getImageView().getX() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getMinX(), 2))
                    + Math.pow(infernoTower.getImageView().getY() - attackingHero.getViewHero().localToScene(attackingHero.getViewHero().getLayoutBounds()).getMinY(), 2));
            if (width < infernoTower.getRange()) {
                hero = attackingHero;
            }
        }
        if (map.getBuildingsMap().size() == 0 || infernoTower.getHitPoints() <= 0) {
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
        while (hero.getHitPoints() >= 0){
            if (infernoTower.getHitPoints() <= 0) {
                isDestroyed = true;
                break;
            }
            hero.setHitPoints(hero.getHitPoints() - (infernoTower.getDamagePerSecond()));
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void myNotify(){
        notify();
    }
}
