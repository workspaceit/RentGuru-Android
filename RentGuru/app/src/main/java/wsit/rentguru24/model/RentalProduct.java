package wsit.rentguru24.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/10/16.
 */
public class RentalProduct extends Product implements Serializable {


    private double currentValue;
    private double rentFee;
    private boolean currentlyAvailable;
    private String availableFrom;
    private String availableTill;



    public RentalProduct()
    {

        this.currentValue = 0.0f;
        this.rentFee = 0.0;
        this.availableFrom = "";
        this.availableTill = "";


    }



    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getRentFee() {
        return rentFee;
    }

    public void setRentFee(double rentFee) {
        this.rentFee = rentFee;
    }



    public boolean isCurrentlyAvailable() {
        return currentlyAvailable;
    }

    public void setCurrentlyAvailable(boolean currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
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


}
