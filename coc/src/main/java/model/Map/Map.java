package model.Map;

import javafx.scene.layout.*;

public abstract class Map extends AnchorPane {
    public Map(int mapId) {
        this.mapId = mapId;
    }

    private final int mapId;

    public int getMapId() {
        return mapId;
    }
    public abstract AnchorPane getMapView();
}
