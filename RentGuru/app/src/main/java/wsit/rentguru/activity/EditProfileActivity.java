package wsit.rentguru.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wsit.rentguru.R;
import wsit.rentguru.adapter.GridviewAdapter;
import wsit.rentguru.asynctask.ChangeProfileImageAsynTask;
import wsit.rentguru.asynctask.UpdateProfileInfoAsynTask;
import wsit.rentguru.model.ImageItem;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText firstName, lastName, email, oldPassword, newPassword;
    private Button changeProfilePicButton, editProfileButton;
    private ImageView profileImage;
    private static final int FILE_SELECT_CODE = 0;
    private String imagePath;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader;
    private ProgressDialog progressDialog;
    private Bitmap resizedBitmap;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private View root;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initiate();
    }

    private void initiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        connectivityManagerInfo=new ConnectivityManagerInfo(this);
        this.firstName = (EditText) findViewById(R.id.first_name);
        root=findViewById(R.id.activity_edit_profile);


        this.lastName = (EditText) findViewById(R.id.last_name);


        this.email = (EditText) findViewById(R.id.email);
        this.oldPassword = (EditText) findViewById(R.id.old_password);
        this.newPassword = (EditText) findViewById(R.id.new_password);
        this.profileImage = (ImageView) findViewById(R.id.profile_image);
        changeProfilePicButton = (Button) findViewById(R.id.change_profile_image_buttton);
        editProfileButton = (Button) findViewById(R.id.edit_profile_button);

        changeProfilePicButton.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);

        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        this.displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        this.setValue();


    }

    private void setValue() {
        this.firstName.setText(Utility.authCredential.getUser().getFirstName());
        this.lastName.setText(Utility.authCredential.getUser().getLastName());
        this.email.setText(Utility.authCredential.getEmail());
        if (Utility.authCredential.getUser().getProfilePicture().getOriginal().getPath().equals("")) {
            this.profileImage.setVisibility(View.GONE);
        } else {
            if (this.profileImage.getVisibility()==View.GONE)
                this.profileImage.setVisibility(View.VISIBLE);
            this.imageLoader.displayImage(Utility.profileImageUrl + Utility.authCredential.getUser().getProfilePicture().getOriginal().getPath(),
                    this.profileImage, displayImageOptions);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == changeProfilePicButton) {
            showFileChooser();
        }else if (v==editProfileButton){
            if (connectivityManagerInfo.isConnectedToInternet()){
                if (verifyInput()){
                    startProgressDailog("Updating Profile Information....");
                    new UpdateProfileInfoAsynTask(this,firstName.getText().toString(),lastName.getText().toString(),
                            email.getText().toString(),oldPassword.getText().toString(),newPassword.getText().toString()).execute();
                }
            }
        }

    }


    private boolean verifyInput(){
        String email = this.email.getText().toString();
        CharSequence input = email;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (firstName.getText().toString().equals("")){
            ShowNotification.showToast(this,"First Name is required");
            firstName.requestFocus();
            return false;
        }else if (lastName.getText().toString().equals("")){
            ShowNotification.showToast(this,"Last Name is required");
            lastName.requestFocus();
            return false;
        }else if (!matcher.matches()) {
            ShowNotification.showToast(this, "Please Enter a valid Email ID");
            this.email.requestFocus();
            return false;
        }

        if (!newPassword.getText().toString().equals("")){
            if (newPassword.getText().toString().length()<6){
                ShowNotification.showToast(this,"Your New Password must be of 6 character");
                newPassword.requestFocus();
                return false;
            }

            if (oldPassword.getText().toString().equals("") || oldPassword.getText().toString().length()<6){
                ShowNotification.showToast(this,"Old Password is not valid");
                oldPassword.requestFocus();
                return false;

            }
        }

        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("uri", "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    path = Utility.getPath(this, uri);
                    Log.d("path", "File Path: " + path);
                    // Get the file instance
                    //File file = new File(path);
                    // Initiate the upload

                    this.imagePath = path;


                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = 1;
                    options.inJustDecodeBounds = false;
                    options.inTempStorage = new byte[16 * 1024];

                    Bitmap bmp = BitmapFactory.decodeFile(path, options);
                    this.resizedBitmap = Bitmap.createScaledBitmap(bmp, 100, 150, false);


                    startProgressDailog("Uploading Profile Picture...");
                    new ChangeProfileImageAsynTask(this).execute(this.imagePath);


                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void profilePicUpdateResult(String resp, boolean flag) {
        if (flag == true) {
            new UpdateProfileInfoAsynTask(this, resp).execute();
        } else {
            progressDialog.dismiss();
        }

    }

    public void updateProfileInfo(boolean flag, int indicator) {
        progressDialog.dismiss();
        if (indicator==1) {
            if (flag == true) {
                if (this.profileImage.getVisibility() == View.GONE) {
                    this.profileImage.setVisibility(View.VISIBLE);
                }

                this.profileImage.setImageBitmap(resizedBitmap);

                ShowNotification.showSnacksBarLong(this,root,"Profile Image Changed Successfully");

            } else {
                ShowNotification.showSnacksBarLong(this,root,"Something went wrong.. Try Again later");

            }
        }else {
            if (flag){
                ShowNotification.showSnacksBarLong(this,root,"Profile Information updated Successfully");

            }else{
                ShowNotification.showSnacksBarLong(this,root,"Something went wrong.. Try Again later");
            }
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startProgressDailog(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
