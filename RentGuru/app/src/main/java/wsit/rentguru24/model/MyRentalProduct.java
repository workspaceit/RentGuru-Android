package wsit.rentguru24.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class MyRentalProduct extends RentalProduct implements Serializable {

 private ArrayList<RentInf> rentInf;

    public MyRentalProduct()
    {
        this.rentInf = new ArrayList<RentInf>();

    }

    public ArrayList<RentInf> getRentInf() {
        return rentInf;
    }

    public void setRentInf(ArrayList<RentInf> rentInf) {
        this.rentInf = rentInf;
    }
}
