package model.Map;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.building.*;

import java.util.ArrayList;

public class Map4 extends Map {
    public Map4() {
        super(4);
        this.capacityMap4 = 100;
        this.buildingsMap4 = new ArrayList<>();
    }
    private final int capacityMap4;
    private final ArrayList<Node> buildingsMap4;
    public int getCapacityMap() {
        return capacityMap4;
    }
    public ArrayList<Node> getBuildingsMap() {
        return buildingsMap4;
    }

    @Override
    public AnchorPane getMapView() {
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        ElixirCollector elixirCollector1 = new ElixirCollector(510, 220);
        GoldMine goldMine1 = new GoldMine(440, 221);
        ArcherTower archerTower4 = new ArcherTower(457, 160);
        ArcherTower archerTower = new ArcherTower(460, 256);
        ArcherTower archerTower2 = new ArcherTower(360, 256);
        ArcherTower archerTower3 = new ArcherTower(560, 256);
        ArcherTower archerTower1 = new ArcherTower(460, 416);
        Tesla tesla = new Tesla(525, 280);
        InfernoTower infernoTower = new InfernoTower(400, 340);
        Tesla tesla2 = new Tesla(420, 280);
        InfernoTower infernoTower2 = new InfernoTower(570, 340);
        TownHall townHall = new TownHall(450, 316);
        ElixirCollector elixirCollector = new ElixirCollector(555, 405);
        GoldMine goldMine = new GoldMine(400, 410);
        Barrack barrack = new Barrack(473, 490);
        Tesla tesla1 = new Tesla(400, 460);
        InfernoTower infernoTower1 = new InfernoTower(550, 460);
        buildingsMap4.add(archerTower4);
        buildingsMap4.add(elixirCollector1);
        buildingsMap4.add(goldMine1);
        buildingsMap4.add(archerTower);
        buildingsMap4.add(archerTower2);
        buildingsMap4.add(archerTower3);
        buildingsMap4.add(townHall);
        buildingsMap4.add(tesla2);
        buildingsMap4.add(infernoTower2);
        buildingsMap4.add(archerTower1);
        buildingsMap4.add(tesla);
        buildingsMap4.add(infernoTower);
        buildingsMap4.add(elixirCollector);
        buildingsMap4.add(goldMine);
        buildingsMap4.add(barrack);
        buildingsMap4.add(tesla1);
        buildingsMap4.add(infernoTower1);

        for (Node node : buildingsMap4) {
            if (node instanceof Building)
                root.getChildren().add(((Building) node).getImageView());
        }
        for (Node node : buildingsMap4) {
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
