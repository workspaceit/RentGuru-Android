package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;


import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.PaypalAccountSettingsActivity;
import wsit.rentguru.model.UserPaypalCredential;

/**
 * Created by Tomal on 11/2/2016.
 */

public class GetPayPalEmailAsynTask  extends AsyncTask<String,String,UserPaypalCredential> {

    private PaypalAccountSettingsActivity paypalAccountSettingsActivity;
    private ProgressDialog progressDialog;

    public GetPayPalEmailAsynTask(PaypalAccountSettingsActivity paypalAccountSettingsActivity){
        this.paypalAccountSettingsActivity=paypalAccountSettingsActivity;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(paypalAccountSettingsActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Getting Paypal Email...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected UserPaypalCredential doInBackground(String... strings) {
        return new AuthenticationService().getPaypalEmail();
    }

    @Override
    protected void onPostExecute(UserPaypalCredential userPaypalCredential) {
        super.onPostExecute(userPaypalCredential);
        progressDialog.dismiss();
        paypalAccountSettingsActivity.setEmail(userPaypalCredential);


    }
}
