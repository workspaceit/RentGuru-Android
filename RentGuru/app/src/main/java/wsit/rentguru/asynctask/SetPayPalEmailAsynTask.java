package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;


import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.PaypalAccountSettingsActivity;

/**
 * Created by Tomal on 11/2/2016.
 */

public class SetPayPalEmailAsynTask extends AsyncTask<String,String,Boolean> {

    private PaypalAccountSettingsActivity paypalAccountSettingsActivity;
    private ProgressDialog progressDialog;


    public SetPayPalEmailAsynTask(PaypalAccountSettingsActivity paypalAccountSettingsActivity){
        this.paypalAccountSettingsActivity=paypalAccountSettingsActivity;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(paypalAccountSettingsActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Changing Paypal Email...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String email=strings[0];
        return new AuthenticationService().setPaypalEmail(email);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        paypalAccountSettingsActivity.chnageEmail(aBoolean);
    }
}
