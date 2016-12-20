package wsit.rentguru24.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/8/16.
 */
public class ImageItem implements Serializable {
    private Bitmap image;


    public ImageItem(Bitmap image) {
        super();
        this.image = image;

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}