package wsit.rentguru.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
