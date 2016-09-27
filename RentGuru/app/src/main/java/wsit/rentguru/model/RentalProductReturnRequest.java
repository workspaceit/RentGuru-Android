package wsit.rentguru.model;

/**
 * Created by workspaceinfotech on 8/30/16.
 */
public class RentalProductReturnRequest {

    private int id;
    private String remarks;
    private Boolean isExpired;
    private String createdDate;

    public RentalProductReturnRequest()
    {
        this.id = 0;
        this.remarks = "";
        this.isExpired = false;
        this.createdDate = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
