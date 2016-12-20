package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import wsit.rentguru24.Service.ProductRenterService;
import wsit.rentguru24.activity.BookingRequestDetailsActivity;

/**
 * Created by Tomal on 11/17/2016.
 */

public class ProductBookingAsynTask extends AsyncTask<String,String,Boolean> {

    private Context context;
    private int type;
    private ProgressDialog progressDialog;
    private int id;

    public ProductBookingAsynTask(Context context, int id, int type) {
        this.context = context;
        this.id = id;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Processing...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }



    @Override
    protected Boolean doInBackground(String... params) {
        if (type==1)
            return new ProductRenterService().cancelRequest(this.id);
        else if (type==2)
            return new ProductRenterService().returnProduct(this.id);
        else
            return false;
   }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        if (context instanceof BookingRequestDetailsActivity){
            if (type==1)
                ((BookingRequestDetailsActivity)context).cancelComplete(aBoolean);
            else if (type==2)
                ((BookingRequestDetailsActivity)context).retunProductComplete(aBoolean);
        }
    }
}
