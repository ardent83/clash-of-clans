package model.building;

import javafx.scene.Node;

import java.util.Collection;

public class TownHall extends Building {
    public TownHall(double x, double y) {
        super(BuildingType.REFERENCES, 6800, "town.png", 120, 120, x, y);
    }
}
