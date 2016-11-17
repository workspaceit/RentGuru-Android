package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import wsit.rentguru.Service.ProductOwnerRentService;
import wsit.rentguru.activity.RentRequestOrderDetailsActivity;
import wsit.rentguru.utility.ShowNotification;

/**
 * Created by Tomal on 11/17/2016.
 */

public class RecieveRentalProductAyncTask extends AsyncTask<String,String,Boolean> {
    private Context context;
    private int rentalProductReturnId;
    private int type;
    private ProgressDialog progressDialog;

    public RecieveRentalProductAyncTask(Context context,int rentalProductReturnId, int type){
        this.context=context;
        this.rentalProductReturnId=rentalProductReturnId;
        this.type=type;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        if (type==1)
            return new ProductOwnerRentService().recieveRentalProductConfirm(rentalProductReturnId);
        else if (type==2)
            return new ProductOwnerRentService().recieveRentalProductDispute(rentalProductReturnId);
        else
            return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (context instanceof RentRequestOrderDetailsActivity){
            if (type==1)
                ((RentRequestOrderDetailsActivity)context).reciveConfrimComplete(aBoolean);
            else if (type==2)
                ((RentRequestOrderDetailsActivity)context).recieveDisputeCompete(aBoolean);
        }
    }

}
