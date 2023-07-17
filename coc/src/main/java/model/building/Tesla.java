package model.building;

import javafx.scene.image.ImageView;

public class Tesla extends Defensive {
    public Tesla(double x, double y) {
        super(BuildingType.DEFENSIVE, 900, "tesla.png", 70, 70, 70, 55, x, y);
    }
}
