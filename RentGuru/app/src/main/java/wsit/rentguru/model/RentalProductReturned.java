package wsit.rentguru.model;

import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/30/16.
 */
public class RentalProductReturned {

    private int id;
    private boolean confirm;
    private boolean dispute;
    private boolean isExpired;
    private String createdDate;
    private String renteeRemarks;
    private String renterRemarks;
    private ArrayList<RentalProductReturnedHistory> rentalProductReturnedHistories;


    public RentalProductReturned()
    {
        this.id = 0;
        this.confirm = false;
        this.dispute = false;
        this.isExpired = false;
        this.createdDate = "";
        this.renteeRemarks = "";
        this.renterRemarks = "";
        this.rentalProductReturnedHistories = new ArrayList<RentalProductReturnedHistory>();

    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDispute() {
        return dispute;
    }

    public void setDispute(boolean dispute) {
        this.dispute = dispute;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRenteeRemarks() {
        return renteeRemarks;
    }

    public void setRenteeRemarks(String renteeRemarks) {
        this.renteeRemarks = renteeRemarks;
    }

    public String getRenterRemarks() {
        return renterRemarks;
    }

    public void setRenterRemarks(String renterRemarks) {
        this.renterRemarks = renterRemarks;
    }

    public ArrayList<RentalProductReturnedHistory> getRentalProductReturnedHistories() {
        return rentalProductReturnedHistories;
    }

    public void setRentalProductReturnedHistories(ArrayList<RentalProductReturnedHistory> rentalProductReturnedHistories) {
        this.rentalProductReturnedHistories = rentalProductReturnedHistories;
    }
}
