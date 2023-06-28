package model.hero;

import model.building.BuildingType;

public class DefBalloon extends Hero{
    public DefBalloon(double x, double y) {
        super(690, 5, 198, 5, 10, BuildingType.DEFENSIVE, 60, 70, x, y, "def_balloon.png");
    }
}
