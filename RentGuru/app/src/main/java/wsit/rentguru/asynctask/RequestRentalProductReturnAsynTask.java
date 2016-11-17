package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import wsit.rentguru.Service.ProductOwnerRentService;
import wsit.rentguru.activity.RentRequestOrderDetailsActivity;

/**
 * Created by Tomal on 11/17/2016.
 */

public class RequestRentalProductReturnAsynTask extends AsyncTask<String,String,Boolean> {
    private Context context;
    private ProgressDialog progressDialog;
    private int rentalInfId;

    public RequestRentalProductReturnAsynTask(Context context,int rentalInfId){
        this.context=context;
        this.rentalInfId=rentalInfId;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }



    @Override
    protected Boolean doInBackground(String... params) {
        return new ProductOwnerRentService().requestRentalProductService(rentalInfId);
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        if (context instanceof RentRequestOrderDetailsActivity)
            ((RentRequestOrderDetailsActivity)context).rentRequestComplete(aBoolean);

    }
}
