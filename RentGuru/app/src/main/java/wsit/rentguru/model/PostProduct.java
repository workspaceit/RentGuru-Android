package wsit.rentguru.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/11/16.
 */
public class PostProduct implements Serializable {

    private String name;
    private String description;
    private String profileImageToken;
    private ArrayList<String> otherImagesToken; //JsonString Array eg [1231,4564]
    private String currentValue;
    private String rentFee;
    private String availableFrom;  // UTC / GMT  Date format "dd-MM-yyy"
    private String availableTill; // UTC / GMT  Date format "dd-MM-yyy"
    private ArrayList<Integer>categoryIds; // JsonString Array e,g [1,2]
    private String formattedAddress;
    private RentType rentType;
    private String zip;
    private String city;
    private String state;
    private String lat;
    private String lng;

    public PostProduct()
    {

        this.otherImagesToken = new ArrayList<String>();
        this.categoryIds = new ArrayList<Integer>();
        this.profileImageToken = "";
        this.lat = "0.0";
        this.lng = "0.0";
        this.city = "";
        this.zip = "";
        this.state = "";

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

    public ArrayList<String> getOtherImagesToken() {
        return otherImagesToken;
    }

    public void setOtherImagesToken(ArrayList<String> otherImagesToken) {
        this.otherImagesToken = otherImagesToken;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getProfileImageToken() {
        return profileImageToken;
    }

    public void setProfileImageToken(String profileImageToken) {
        this.profileImageToken = profileImageToken;
    }

    public String getRentFee() {
        return rentFee;
    }

    public void setRentFee(String rentFee) {
        this.rentFee = rentFee;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTill() {
        return availableTill;
    }

    public void setAvailableTill(String availableTill) {
        this.availableTill = availableTill;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public ArrayList<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(ArrayList<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public RentType getRentType() {
        return rentType;
    }

    public void setRentType(RentType rentType) {
        this.rentType = rentType;
    }
}
