package model.Map;

import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.ArrayList;

public abstract class Map {
    public Map(int mapId) {
        this.mapId = mapId;
    }

    private final int mapId;

    public int getMapId() {
        return mapId;
    }
    public abstract AnchorPane getMapView();
    public abstract int getCapacityMap();
    public abstract ArrayList<Node> getBuildingsMap();
}
