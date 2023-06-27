package model.building;

import javafx.scene.image.ImageView;

public class ArcherTower extends Building{
    public ArcherTower(double x, double y) {
        super(BuildingType.DEFENSIVE, 1230, "archerTower.png", 100, 100, x, y);
        this.damagePerSecond = 104;
        this.range = 100;
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
