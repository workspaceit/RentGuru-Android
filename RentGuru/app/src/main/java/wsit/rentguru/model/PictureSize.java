package wsit.rentguru.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/10/16.
 */
public class PictureSize implements Serializable {

    private int width;
    private int height;

    public PictureSize()
    {
        width = 0;
        height = 0;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
