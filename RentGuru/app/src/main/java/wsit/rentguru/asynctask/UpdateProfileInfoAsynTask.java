package wsit.rentguru.asynctask;

import android.text.Editable;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import wsit.rentguru.Service.AuthenticationService;
import wsit.rentguru.activity.EditProfileActivity;

/**
 * Created by Tomal on 11/1/2016.
 */

public class UpdateProfileInfoAsynTask extends AsyncTask<String,String,Boolean> {
    private EditProfileActivity editProfileActivity;
    private String imageToeken;
    private int indicatorFlag;
    private String fname,lname,email,oldPass,newPass;

    public UpdateProfileInfoAsynTask(EditProfileActivity editProfileActivity,String imageToen){
        this.editProfileActivity=editProfileActivity;
        this.imageToeken=imageToen;
        this.indicatorFlag=1;

    }

    public UpdateProfileInfoAsynTask(EditProfileActivity editProfileActivity,String fname,String lname,String email,String oldPas,String newPass){
        this.editProfileActivity=editProfileActivity;
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.oldPass=oldPas;
        this.newPass=newPass;
        indicatorFlag=2;
    }



    @Override
    protected Boolean doInBackground(String... strings) {
        if (indicatorFlag==1){
            return new AuthenticationService().changeProfilePicture(this.imageToeken);
        }else if (indicatorFlag==2){
            return new AuthenticationService().changeProfileInformation(fname,lname,email,oldPass,newPass);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

            editProfileActivity.updateProfileInfo(aBoolean,indicatorFlag);

    }
}
