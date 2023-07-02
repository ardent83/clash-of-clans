package model;

import javafx.scene.image.ImageView;

public class MyImageView extends ImageView {
    public MyImageView(ImageView imageView) {
        this.setImage(imageView.getImage());
        this.setX(imageView.getX());
        this.setY(imageView.getY());
        this.setFitWidth(imageView.getFitWidth());
        this.setFitHeight(imageView.getFitHeight());
    }
}
