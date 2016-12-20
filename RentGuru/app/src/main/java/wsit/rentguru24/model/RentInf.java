package wsit.rentguru24.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class RentInf implements Serializable {

    private int id;
    private String startDate;
    private String endsDate;
    private boolean expired;
    private String createdDate;
    private RentRequest rentRequest;
    private boolean productReturned;
    private boolean productReceived;
    private boolean hasReturnRequest;
    private boolean hasReceiveConfirmation;
    private RentalProductReturnRequest rentalProductReturnRequest;
    private RentalProductReturned rentalProductReturned;
    private boolean isRentComplete;

    public boolean isRentComplete() {
        return isRentComplete;
    }

    public void setRentComplete(boolean rentComplete) {
        isRentComplete = rentComplete;
    }

    public RentInf()
    {

        id = 0;
        startDate = "";
        endsDate = "";
        expired = false;
        createdDate = "";
        rentRequest = new RentRequest();
        productReturned = false;
        productReceived = false;
        hasReturnRequest = false;
        hasReceiveConfirmation = false;
        rentalProductReturnRequest = new RentalProductReturnRequest();
        rentalProductReturned = new RentalProductReturned();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndsDate() {
        return endsDate;
    }

    public void setEndsDate(String endsDate) {
        this.endsDate = endsDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public RentRequest getRentRequest() {
        return rentRequest;
    }

    public void setRentRequest(RentRequest rentRequest) {
        this.rentRequest = rentRequest;
    }

    public boolean isProductReturned() {
        return productReturned;
    }

    public void setProductReturned(boolean productReturned) {
        this.productReturned = productReturned;
    }

    public boolean isProductReceived() {
        return productReceived;
    }

    public void setProductReceived(boolean productReceived) {
        this.productReceived = productReceived;
    }

    public boolean isHasReturnRequest() {
        return hasReturnRequest;
    }

    public void setHasReturnRequest(boolean hasReturnRequest) {
        this.hasReturnRequest = hasReturnRequest;
    }

    public boolean isHasReceiveConfirmation() {
        return hasReceiveConfirmation;
    }

    public void setHasReceiveConfirmation(boolean hasReceiveConfirmation) {
        this.hasReceiveConfirmation = hasReceiveConfirmation;
    }

    public RentalProductReturnRequest getRentalProductReturnRequest() {
        return rentalProductReturnRequest;
    }

    public void setRentalProductReturnRequest(RentalProductReturnRequest rentalProductReturnRequest) {
        this.rentalProductReturnRequest = rentalProductReturnRequest;
    }

    public RentalProductReturned getRentalProductReturned() {
        return rentalProductReturned;
    }

    public void setRentalProductReturned(RentalProductReturned rentalProductReturned) {
        this.rentalProductReturned = rentalProductReturned;
    }
}
