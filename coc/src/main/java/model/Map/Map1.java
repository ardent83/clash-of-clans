package model.Map;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.building.*;

import java.util.ArrayList;

public class Map1 extends Map {
    public Map1() {
        super(1);
        this.capacityMap1 = 20;
        buildingsMap1 = new ArrayList<>();
    }
    private final int capacityMap1;
    private final ArrayList<Node> buildingsMap1;
    public int getCapacityMap() {
        return capacityMap1;
    }
    public ArrayList<Node> getBuildingsMap() {
        return buildingsMap1;
    }
    @Override
    public AnchorPane getMapView() {
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        ArcherTower archerTower = new ArcherTower(460, 256);
        TownHall townHall = new TownHall(450, 316);
        ArcherTower archerTower1 = new ArcherTower(460, 416);
        Tesla tesla = new Tesla(550, 340);
        InfernoTower infernoTower = new InfernoTower(400, 340);
        ElixirCollector elixirCollector = new ElixirCollector(555, 405);
        GoldMine goldMine = new GoldMine(400, 410);
        Barrack barrack = new Barrack(473, 490);
        buildingsMap1.add(archerTower);
        buildingsMap1.add(townHall);
        buildingsMap1.add(archerTower1);
        buildingsMap1.add(tesla);
        buildingsMap1.add(infernoTower);
        buildingsMap1.add(elixirCollector);
        buildingsMap1.add(goldMine);
        buildingsMap1.add(barrack);

        for (Node node : buildingsMap1) {
            if (node instanceof Building)
                root.getChildren().add(((Building) node).getImageView());
        }
        for (Node node : buildingsMap1) {
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
}
