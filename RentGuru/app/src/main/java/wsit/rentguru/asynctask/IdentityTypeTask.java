package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.RegistrationActivity;
import wsit.rentguru.model.IdentityType;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class IdentityTypeTask extends AsyncTask<Boolean, Void, Boolean> {

    ProgressDialog dialog;
    RegistrationActivity mcontext;
    Boolean response;
    AuthenticationService authenticationService;
    private ArrayList<IdentityType> identityTypeArrayList;

    public IdentityTypeTask(RegistrationActivity context)
    {
        this.mcontext = context;
        this.authenticationService = new AuthenticationService();
        this.response = false;
    }


    @Override
    protected Boolean doInBackground(Boolean... params) {

        try {

            identityTypeArrayList =  authenticationService.getIdentityType();

            if(identityTypeArrayList.size()>0)
            {
                response = true;
            }

        }catch (Exception ex)
        {
            response = false;
        }

        return response;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mcontext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
       // dialog.show();

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
       // dialog.dismiss();

        if(response == true)
        {
            mcontext.loadIdentityType(identityTypeArrayList);

        }
        else
        {
            Toast.makeText(mcontext,"Unable to connect to server,try again later",Toast.LENGTH_SHORT).show();
        }

    }









}
