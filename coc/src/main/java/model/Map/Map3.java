package model.Map;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.building.*;

import java.util.ArrayList;

public class Map3 extends Map {
    public Map3() {
        super(3);
        this.capacityMap3 = 80;
        this.buildingsMap3 = new ArrayList<>();
        this.buildMap();
    }
    private final int capacityMap3;
    private final ArrayList<Node> buildingsMap3;
    public int getCapacityMap() {
        return capacityMap3;
    }
    public ArrayList<Node> getBuildingsMap() {
        return buildingsMap3;
    }

    @Override
    public AnchorPane getMapView() {
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        for (Node node : buildingsMap3) {
            if (node instanceof Building)
                root.getChildren().add(((Building) node).getImageView());
        }
        for (Node node : buildingsMap3) {
            if (node instanceof Defensive) {
                ((Building) node).getImageView().setOnMouseEntered(mouseEvent -> {
                    Circle circle = new Circle(((Building) node).getImageView().getX() + ((((Building) node).getImageView().getFitWidth()) / 2),
                            ((Building) node).getImageView().getY() + ((((Building) node).getImageView().getFitHeight()) / 2), ((Defensive) node).getRange());
                    circle.setFill(Color.TRANSPARENT);
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeWidth(2);
                    root.getChildren().add(circle);
                });
                ((Building) node).getImageView().setOnMouseExited(mouseEvent -> {
                    root.getChildren().remove(root.getChildren().size() - 1);
                });
            }
        }
        return root;
    }

    private void buildMap(){
        ElixirCollector elixirCollector1 = new ElixirCollector(510, 220);
        GoldMine goldMine1 = new GoldMine(440, 221);
        ArcherTower archerTower4 = new ArcherTower(457, 160);
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
        buildingsMap3.add(archerTower4);
        buildingsMap3.add(elixirCollector1);
        buildingsMap3.add(goldMine1);
        buildingsMap3.add(archerTower);
        buildingsMap3.add(archerTower2);
        buildingsMap3.add(archerTower3);
        buildingsMap3.add(townHall);
        buildingsMap3.add(archerTower1);
        buildingsMap3.add(tesla);
        buildingsMap3.add(infernoTower);
        buildingsMap3.add(elixirCollector);
        buildingsMap3.add(goldMine);
        buildingsMap3.add(barrack);
        buildingsMap3.add(tesla1);
        buildingsMap3.add(infernoTower1);
    }
}
