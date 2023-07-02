package model.hero;
public class Dragon extends Hero{
    public Dragon(double x, double y) {
        super(3400, 20, 310, 30, 16, null, 120, 120, x, y, "dragonL.gif", "dragon_attackL.gif", "dragonR.gif", "dragon_attackR.gif");
        getImageViews().get(1).setFitWidth(170);
        getImageViews().get(1).setFitHeight(120);
        getImageViews().get(3).setFitWidth(170);
        getImageViews().get(3).setFitHeight(120);
    }
}
