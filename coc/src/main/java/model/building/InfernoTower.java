package model.building;

import javafx.scene.image.ImageView;

import java.util.random.RandomGenerator;

public class InfernoTower extends Defensive {
    public InfernoTower(double x, double y) {
        super(BuildingType.DEFENSIVE, 2700, "inferno_tower.png", 50, 75, 90, 400, x, y);
    }
}
