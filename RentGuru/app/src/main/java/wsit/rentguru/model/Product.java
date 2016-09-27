package wsit.rentguru.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/17/16.
 */
public class Product implements Serializable {


    private int id;
    private String name;
    private String description;
    private Picture profileImage;
    private ArrayList<Picture> otherImages;
    private boolean active;
    private Float averageRating;
    Boolean isLiked;
    private boolean reviewStatus;
    private AppCredential owner;
    private ArrayList<ProductCategory> productCategories;
    private ProductLocation productLocation;
    private RentType rentType;


    public Product()
    {
        this.description = "";
        this.name = "";
        this.otherImages = new ArrayList<Picture>();
        this.productCategories = new ArrayList<ProductCategory>();
        this.productLocation = new ProductLocation();
        this.id = 0;
        this.profileImage = new Picture();
        this.owner = new AppCredential();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Picture getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Picture profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(boolean reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public AppCredential getOwner() {
        return owner;
    }

    public void setOwner(AppCredential owner) {
        this.owner = owner;
    }

    public ArrayList<Picture> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(ArrayList<Picture> otherImages) {
        this.otherImages = otherImages;
    }

    public ArrayList<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(ArrayList<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public ProductLocation getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(ProductLocation productLocation) {
        this.productLocation = productLocation;
    }

    public RentType getRentType() {
        return rentType;
    }

    public void setRentType(RentType rentType) {
        this.rentType = rentType;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
