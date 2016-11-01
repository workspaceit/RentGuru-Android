package wsit.rentguru.model;

/**
 * Created by Tomal on 11/1/2016.
 */

public class BannerImage {
    private int id;
    private String imagePath;
    private String url;
    private String  createdDate;

    public BannerImage(){
        this.id=0;
        this.imagePath="";
        this.url="";
        this.createdDate="";

    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
