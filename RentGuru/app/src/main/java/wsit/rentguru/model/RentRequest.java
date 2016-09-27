package wsit.rentguru.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/18/16.
 */
public class RentRequest implements Serializable {

    private int id;
    private RentalProduct rentalProduct;
    private AppCredential requestedBy;
    private ArrayList<RentRequest> requestExtension;
    private boolean requestCancel;
    private String startDate;   // yyyy-MM-dd
    private String endDate;   // yyyy-MM-dd
    private Boolean approve;
    private Boolean disapprove;
    private boolean isExtension;
    private String remark;


    public RentRequest()
    {
        this.id = 0;
        this.rentalProduct = new RentalProduct();
        this.requestedBy = new AppCredential();
        this.requestExtension = new ArrayList<RentRequest>();
        this.requestCancel = false;
        this.startDate = "";
        this.endDate = "";
        this.approve = false;
        this.disapprove = false;
        this.isExtension = false;
        this.remark = "";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RentalProduct getRentalProduct() {
        return rentalProduct;
    }

    public void setRentalProduct(RentalProduct rentalProduct) {
        this.rentalProduct = rentalProduct;
    }

    public AppCredential getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(AppCredential requestedBy) {
        this.requestedBy = requestedBy;
    }

    public ArrayList<RentRequest> getRequestExtension() {
        return requestExtension;
    }

    public void setRequestExtension(ArrayList<RentRequest> requestExtension) {
        this.requestExtension = requestExtension;
    }

    public boolean isRequestCancel() {
        return requestCancel;
    }

    public void setRequestCancel(boolean requestCancel) {
        this.requestCancel = requestCancel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public Boolean getDisapprove() {
        return disapprove;
    }

    public void setDisapprove(Boolean disapprove) {
        this.disapprove = disapprove;
    }

    public boolean isExtension() {
        return isExtension;
    }

    public void setIsExtension(boolean isExtension) {
        this.isExtension = isExtension;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
