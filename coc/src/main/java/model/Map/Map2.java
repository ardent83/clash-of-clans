package model.Map;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.building.*;

import java.util.ArrayList;

public class Map2 extends Map {
    public Map2() {
        super(2);
        this.capacityMap2 = 80;
        this.buildingsMap2 = new ArrayList<>();
        this.buildMap();
    }
    private final int capacityMap2;
    private final ArrayList<Node> buildingsMap2;
    public int getCapacityMap() {
        return capacityMap2;
    }
    public ArrayList<Node> getBuildingsMap() {
        return buildingsMap2;
    }
    @Override
    public AnchorPane getMapView() {
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        for (Node node : buildingsMap2) {
            if (node instanceof Building)
                root.getChildren().add(((Building) node).getImageView());
        }
        return root;
    }

    private void buildMap(){
        ArcherTower archerTower = new ArcherTower(460, 256);
        ArcherTower archerTower2 = new ArcherTower(360, 256);
        ArcherTower archerTower3 = new ArcherTower(560, 256);
        TownHall townHall = new TownHall(450, 316);
        ArcherTower archerTower1 = new ArcherTower(460, 416);
        Tesla tesla = new Tesla(550, 340);
        InfernoTower infernoTower = new InfernoTower(400, 340);
        ElixirCollector elixirCollector = new ElixirCollector(555, 405);
        GoldMine goldMine = new GoldMine(400, 410);
        Barrack barrack = new Barrack(473, 490);
        Tesla tesla1 = new Tesla(400, 460);
        InfernoTower infernoTower1 = new InfernoTower(550, 460);
        buildingsMap2.add(archerTower);
        buildingsMap2.add(archerTower2);
        buildingsMap2.add(archerTower3);
        buildingsMap2.add(townHall);
        buildingsMap2.add(archerTower1);
        buildingsMap2.add(tesla);
        buildingsMap2.add(infernoTower);
        buildingsMap2.add(elixirCollector);
        buildingsMap2.add(goldMine);
        buildingsMap2.add(barrack);
        buildingsMap2.add(tesla1);
        buildingsMap2.add(infernoTower1);
    }
}
