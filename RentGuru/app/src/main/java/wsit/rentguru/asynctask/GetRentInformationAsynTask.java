package wsit.rentguru.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import wsit.rentguru.Service.RentService;
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
        return new RentService().getRentInf(rentId);
    }

    @Override
    protected void onPostExecute(RentInf rentInf) {
        super.onPostExecute(rentInf);
        if (context instanceof RentRequestOrderDetailsActivity){
            ((RentRequestOrderDetailsActivity)context).completeGetRentInf(rentInf);

        }
    }
}
