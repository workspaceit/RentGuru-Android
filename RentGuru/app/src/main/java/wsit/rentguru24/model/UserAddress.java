package wsit.rentguru24.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class UserAddress implements Serializable {

    private int id;
    private String address;
    private String zip;
    private String city;
    private String state;
    private String createdDate;
    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public UserAddress()
    {
        this.id = 0;
        this.address = "";
        this.zip = "";
        this.city = "";
        this.state = "";
        this.state = "";
        this.createdDate = "";

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
