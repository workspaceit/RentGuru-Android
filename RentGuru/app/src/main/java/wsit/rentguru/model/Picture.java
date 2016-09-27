package wsit.rentguru.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/10/16.
 */
public class Picture implements Serializable {

    private PictureDetails original;
    private ArrayList<PictureDetails> thumb;

    public Picture()
    {
        original = new PictureDetails();
        thumb = new ArrayList<PictureDetails>();
    }

    public PictureDetails getOriginal() {
        return original;
    }

    public void setOriginal(PictureDetails original) {
        this.original = original;
    }

    public ArrayList<PictureDetails> getThumb() {
        return thumb;
    }

    public void setThumb(ArrayList<PictureDetails> thumb) {
        this.thumb = thumb;
    }
}
