package wsit.rentguru.asynctask;

import android.app.ProgressDialog;

import java.io.IOException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.EditProfileActivity;

/**
 * Created by Tomal on 11/1/2016.
 */

public class ChangeProfileImageAsynTask extends AsyncTask<String,String,String> {
    private EditProfileActivity editProfileActivity;
    private AuthenticationService authenticationService;


    public ChangeProfileImageAsynTask(EditProfileActivity editProfileActivity){
        this.editProfileActivity=editProfileActivity;
        this.authenticationService=new AuthenticationService();
    }




    @Override
    protected String doInBackground(String... strings) {
        String imagepath=strings[0];
        String resp="";
        try {
            resp=authenticationService.uploadNewProfilePic(imagepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        System.out.println("tomal "+s);
        if (s.equals("")){
            editProfileActivity.profilePicUpdateResult(s,false);
        }else {
            editProfileActivity.profilePicUpdateResult(s,true);
        }
    }

}
