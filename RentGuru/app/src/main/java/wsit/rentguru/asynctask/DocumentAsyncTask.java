package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.RegistrationActivity;
import wsit.rentguru.model.IdentityType;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class DocumentAsyncTask extends AsyncTask<String, Void, String> {

    ProgressDialog dialog;
    RegistrationActivity mcontext;
    String response;
    AuthenticationService authenticationService;
    String mfilepath;
    public DocumentAsyncTask(RegistrationActivity context,String filepath)
    {
        this.mcontext = context;
        this.authenticationService = new AuthenticationService();
        this.response = "";
        this.mfilepath = filepath;
    }



    @Override
    protected String doInBackground(String... params) {

        try {

            response  =  authenticationService.sendDocument(mfilepath);

        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            response = "";
        }

        return response;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mcontext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        dialog.dismiss();

        if(response != "")
        {
            mcontext.fileUploaded(response);

        }
        else
        {
            Toast.makeText(mcontext, "Unable to upload it", Toast.LENGTH_SHORT).show();
        }


    }
}
