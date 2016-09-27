package wsit.rentguru.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/10/16.
 */
public class PictureDetails implements Serializable {


    private String path;
    private String type;
    private PictureSize size;

    public PictureDetails()
    {
        path = "";
        type = "";
        size = new PictureSize();

    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PictureSize getSize() {
        return size;
    }

    public void setSize(PictureSize size) {
        this.size = size;
    }
}
