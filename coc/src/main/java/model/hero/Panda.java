package model.hero;
public class Panda extends Hero{
    public Panda(double x, double y) {
        super(1500, 10, 150, 0, 24, null, 50, 50, 1, x, y, "pandaL.gif", "panda_attackL.gif", "pandaR.gif", "panda_attackR.gif");
        getImageViews().get(1).setFitWidth(55);
        getImageViews().get(1).setFitHeight(45);
        getImageViews().get(3).setFitWidth(55);
        getImageViews().get(3).setFitHeight(45);
    }
}
