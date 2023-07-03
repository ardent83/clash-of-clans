package model.hero;

import model.building.BuildingType;

public class GoblinBalloon extends Hero{
    public GoblinBalloon(double x, double y) {
        super(300, 2, 52, 0, 32, BuildingType.REFERENCES, 60, 80, x, y, "goblin_balloon.png", "goblin_attack.gif");
        getImageViews().get(1).setFitHeight(113);
    }
}
