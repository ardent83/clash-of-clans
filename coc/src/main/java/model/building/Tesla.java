package model.building;

import javafx.scene.image.ImageView;

public class Tesla extends Building {
    public Tesla(double x, double y) {
        super(BuildingType.DEFENSIVE, 900, "tesla.png", 70, 70, x, y);
        this.damagePerSecond = 110;
        this.range = 70;
    }
    private final int damagePerSecond;
    private final int range;
    public int getDamagePerSecond() {
        return damagePerSecond;
    }
    public int getRange() {
        return range;
    }

}
