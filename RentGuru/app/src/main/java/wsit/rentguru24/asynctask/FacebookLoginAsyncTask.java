package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru24.Service.AuthenticationService;
import wsit.rentguru24.activity.LoginActivity;
import wsit.rentguru24.model.ResponseStat;

/**
 * Created by workspaceinfotech on 9/5/16.
 */
public class FacebookLoginAsyncTask extends AsyncTask<Boolean, Void, ResponseStat> {

    private LoginActivity mcontext;
    private ProgressDialog dialog;
    private ResponseStat response;
    private AuthenticationService authenticationService;
    private String accessToken;

    public FacebookLoginAsyncTask(LoginActivity loginActivity,String accessToken)
    {
        this.mcontext = loginActivity;
        this.authenticationService = new AuthenticationService();
        this.response = new ResponseStat();
        this.accessToken = accessToken;
    }

    @Override
    protected ResponseStat doInBackground(Boolean... params) {

        try {

            response  =  authenticationService.facebookRegistration(accessToken);

        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());

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
    protected void onPostExecute(ResponseStat aResponse) {
        super.onPostExecute(aResponse);
        dialog.dismiss();

        if(response !=null)
        {
            if(response.isStatus()==false)
            {
                Toast.makeText(mcontext, response.getRequestErrors().get(0).getMsg(), Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(mcontext, response.getMsg() , Toast.LENGTH_SHORT).show();
                mcontext.doneLogin();

            }


        }
        else
        {
            //Toast.makeText(postProductFirstFragment, "Unable to communicate with server", Toast.LENGTH_SHORT).show();
        }

    }
}