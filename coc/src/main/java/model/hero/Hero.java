package model.hero;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.building.BuildingType;

import java.util.ArrayList;

public abstract class Hero extends Node {
    public Hero(int hitPoints, int housingSpace, int damagePerSecond, int rang, int movementSpeed, BuildingType favoriteTarget, double width, double height, double x, double y, String ... paths) {
        this.hitPoints = hitPoints;
        this.housingSpace = housingSpace;
        this.damagePerSecond = damagePerSecond;
        this.rang = rang;
        this.movementSpeed = movementSpeed;
        this.favoriteTarget = favoriteTarget;
        this.imageViews = new ArrayList<>();
        for (String path : paths){
            ImageView imageView = new ImageView(new Image(path, true));
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setX(x);
            imageView.setY(y);
            imageViews.add(imageView);
        }
    }

    private int hitPoints;
    private final int housingSpace;
    private final int damagePerSecond;
    private final int rang;
    private final int movementSpeed;
    private final BuildingType favoriteTarget;
    private final ArrayList<ImageView> imageViews;
    private ImageView viewHero;

    public ImageView getViewHero() {
        return viewHero;
    }

    public void setViewHero(ImageView viewHero) {
        this.viewHero = viewHero;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHousingSpace() {
        return housingSpace;
    }

    public int getDamagePerSecond() {
        return damagePerSecond;
    }

    public int getRang() {
        return rang;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public BuildingType getFavoriteTarget() {
        return favoriteTarget;
    }

    public ArrayList<ImageView> getImageViews() {
        return imageViews;
    }
}
