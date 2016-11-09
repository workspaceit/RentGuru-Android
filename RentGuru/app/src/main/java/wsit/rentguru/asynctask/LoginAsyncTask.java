package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.HomeActivity;
import wsit.rentguru.activity.LoginActivity;
import wsit.rentguru.activity.RegistrationActivity;
import wsit.rentguru.model.Login;
import wsit.rentguru.model.Registration;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/5/16.
 */
public class LoginAsyncTask extends AsyncTask<Boolean, Void, ResponseStat> {


    ProgressDialog dialog;
    LoginActivity mcontext;
    ResponseStat response;
    AuthenticationService authenticationService;
    Login login;


    public LoginAsyncTask(LoginActivity context,Login login)
    {
        this.mcontext = context;
        this.authenticationService = new AuthenticationService();
        this.response = new ResponseStat();
        this.login = login;
    }



    @Override
    protected ResponseStat doInBackground(Boolean... params) {

        try {

            response  =  authenticationService.requestLogin(login);

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
                Toast.makeText(mcontext, "Wrong Username or Password", Toast.LENGTH_SHORT).show();

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
