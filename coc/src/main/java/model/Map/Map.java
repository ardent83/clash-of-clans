package model.Map;

import javafx.scene.Node;
import javafx.scene.layout.*;
import model.hero.Hero;

import java.util.ArrayList;

public abstract class Map {
    public Map(int mapId) {
        this.mapId = mapId;
        this.attackingHeroes = new ArrayList<>();
    }
    private final int mapId;
    private final ArrayList<Hero> attackingHeroes;

    public int getMapId() {
        return mapId;
    }
    public ArrayList<Hero> getAttackingHeroes() {
        return attackingHeroes;
    }
    public abstract AnchorPane getMapView();
    public abstract int getCapacityMap();
    public abstract ArrayList<Node> getBuildingsMap();
}
