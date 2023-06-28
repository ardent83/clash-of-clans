package model;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.building.*;

import java.util.ArrayList;

public class Maps extends AnchorPane {
    private static final int capacityMap1;
    private static final int capacityMap2;
    private static final int capacityMap3;
    private static final int capacityMap4;
    private static final ArrayList<Node> buildingsMap1;
    private static final ArrayList<Node> buildingsMap2;
    private static final ArrayList<Node> buildingsMap3;
    private static final ArrayList<Node> buildingsMap4;
    static {
        buildingsMap1  = new ArrayList<>();
        buildingsMap2  = new ArrayList<>();
        buildingsMap3  = new ArrayList<>();
        buildingsMap4  = new ArrayList<>();
        capacityMap1 = 40;
        capacityMap2 = 60;
        capacityMap3 = 80;
        capacityMap4 = 100;
    }

    public static int getCapacityMap1() {
        return capacityMap1;
    }

    public static int getCapacityMap2() {
        return capacityMap2;
    }

    public static int getCapacityMap3() {
        return capacityMap3;
    }

    public static int getCapacityMap4() {
        return capacityMap4;
    }

    public static ArrayList<Node> getBuildingsMap1() {
        return buildingsMap1;
    }

    public static ArrayList<Node> getBuildingsMap2() {
        return buildingsMap2;
    }

    public static ArrayList<Node> getBuildingsMap3() {
        return buildingsMap3;
    }

    public static ArrayList<Node> getBuildingsMap4() {
        return buildingsMap4;
    }

    public static AnchorPane getMap1() {
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

    public static AnchorPane getMap2() {
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
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

        for (Node node : buildingsMap2) {
            if (node instanceof Building)
                root.getChildren().add(((Building) node).getImageView());
        }
        for (Node node : buildingsMap2) {
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

    public static AnchorPane getMap3() {
        AnchorPane root = new AnchorPane();
        root.setBackground(new Background(new BackgroundImage(new Image("classic12.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
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

    public static AnchorPane getMap4() {
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
