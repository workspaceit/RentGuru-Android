package wsit.rentguru.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import wsit.rentguru.Service.ProductOwnerRentService;
import wsit.rentguru.activity.BookingRequestDetailsActivity;
import wsit.rentguru.activity.RentRequestOrderDetailsActivity;
import wsit.rentguru.model.RentInf;

/**
 * Created by Tomal on 11/16/2016.
 */

public class GetRentInformationAsynTask extends AsyncTask<String,String,RentInf> {
    private Context context;
    private int rentId;
    public GetRentInformationAsynTask(Context context,int rentId){
        this.context=context;
        this.rentId=rentId;

    }

    @Override
    protected RentInf doInBackground(String... params) {
        return new ProductOwnerRentService().getRentInf(rentId);
    }

    @Override
    protected void onPostExecute(RentInf rentInf) {
        super.onPostExecute(rentInf);
        if (context instanceof RentRequestOrderDetailsActivity){
            ((RentRequestOrderDetailsActivity)context).completeGetRentInf(rentInf);
        }else if (context instanceof BookingRequestDetailsActivity)
            ((BookingRequestDetailsActivity)context).completeGetRentInf(rentInf);
    }
}
