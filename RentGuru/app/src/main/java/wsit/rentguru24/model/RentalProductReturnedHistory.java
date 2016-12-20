package wsit.rentguru24.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/30/16.
 */
public class RentalProductReturnedHistory implements Serializable {

    private int id;
    private boolean confirm;
    private boolean dispute;
    private String created;

    public RentalProductReturnedHistory()
    {
        this.id = 0;
        this.confirm = false;
        this.dispute = false;
        this.created = "";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isDispute() {
        return dispute;
    }

    public void setDispute(boolean dispute) {
        this.dispute = dispute;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
