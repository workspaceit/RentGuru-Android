package wsit.rentguru24.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/11/16.
 */
public class ProductLocation implements Serializable {

    private int id;
    private int productId;
    private String city;
    private String formattedAddress;
    private String zip;
    private double lat ;
    private double lng ;
    private State state;

    public ProductLocation(){
        this.id=0;
        this.productId=0;
        this.city="";
        this.formattedAddress="";
        this.zip="";
        this.lat=0;
        this.lng=0;
        this.state=new State();

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
