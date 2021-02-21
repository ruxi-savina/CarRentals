package ControllerFX;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImageView {
    private String path;
    private ImageView imageView = new ImageView();

    CustomImageView(String path) {
        imageView.setImage(new Image(path));
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);
        imageView.onMouseClickedProperty();
    }

    public void setImageView(ImageView imageView) {
        imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
