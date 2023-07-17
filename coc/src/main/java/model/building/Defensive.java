package model.building;

public abstract class Defensive extends Building{
    public Defensive(BuildingType buildingType, int hitPoints, String path, double width, double height, int rang, int damagePerSecond, double x, double y) {
        super(buildingType, hitPoints, path, width, height, x, y);
        this.range = rang;
        this.damagePerSecond = damagePerSecond;
    }
    private final int range;
    private final int damagePerSecond;
    public int getRange() {
        return range;
    }
    public int getDamagePerSecond() {
        return damagePerSecond;
    }
}
