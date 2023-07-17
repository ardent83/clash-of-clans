package model.hero;

import model.building.BuildingType;

public class DefBalloon extends Hero{
    public DefBalloon(double x, double y) {
        super(690, 10, 198, 0, 10, BuildingType.DEFENSIVE, 60, 70, 2, x, y, "def_balloon.png","balloon_attack.gif");
        getImageViews().get(1).setFitHeight(102);
    }
}
