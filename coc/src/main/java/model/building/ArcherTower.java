package model.building;

import javafx.scene.image.ImageView;

public class ArcherTower extends Defensive {
    public ArcherTower(double x, double y) {
        super(BuildingType.DEFENSIVE, 1230, "archerTower.png", 100, 100, 100, 104, x, y);
    }
}
