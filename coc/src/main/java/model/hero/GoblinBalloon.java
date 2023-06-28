package model.hero;

import model.building.BuildingType;

public class GoblinBalloon extends Hero{
    public GoblinBalloon(double x, double y) {
        super(101, 1, 52, 5, 32, BuildingType.REFERENCES, 60, 80, x, y, "goblin_balloon.png");
    }
}
