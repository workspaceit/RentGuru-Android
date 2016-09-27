package wsit.rentguru.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.asynctask.DocumentAsyncTask;
import wsit.rentguru.asynctask.IdentityTypeTask;
import wsit.rentguru.asynctask.RegistrationAsyncTask;
import wsit.rentguru.model.IdentityType;
import wsit.rentguru.model.Registration;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.Utility;

public class RegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText firstName,lastName,email,password;
    private Spinner identityType;
    private Button upload,signUp;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private static final int FILE_SELECT_CODE = 0;
    private Registration registration;
    private ArrayList<IdentityType> identityTypeArrayList;


    private void initiate()
    {


        this.email = (EditText)findViewById(R.id.email);
        this.password = (EditText)findViewById(R.id.password);

        this.email.setOnFocusChangeListener(this);
        this.password.setOnFocusChangeListener(this);

        this.firstName = (EditText)findViewById(R.id.first_name);
        this.lastName = (EditText)findViewById(R.id.last_name);

        this.firstName.setOnFocusChangeListener(this);
        this.lastName.setOnFocusChangeListener(this);

        this.identityType = (Spinner)findViewById(R.id.identity);
        this.identityType.setOnItemSelectedListener(this);

        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);

        this.upload = (Button)findViewById(R.id.uploadIdentity);
        this.upload.setOnClickListener(this);

        this.registration = new Registration();



        this.signUp = (Button)findViewById(R.id.signUp);
        this.signUp.setOnClickListener(this);



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initiate();

        if(connectivityManagerInfo.isConnectedToInternet() == true)
        {
            new IdentityTypeTask(this).execute();
        }



    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(v == firstName)
        {
            if(hasFocus){


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    firstName.setBackgroundDrawable(getResources().getDrawable(R.drawable.focus_border));
                } else {
                    firstName.setBackground(getResources().getDrawable(R.drawable.focus_border));
                }


            }else {


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    firstName.setBackgroundDrawable( getResources().getDrawable(R.drawable.view_border) );
                } else {
                    firstName.setBackground( getResources().getDrawable(R.drawable.view_border));
                }


            }


        }

        else if(v == lastName)
        {
            if(hasFocus){


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    lastName.setBackgroundDrawable(getResources().getDrawable(R.drawable.focus_border));
                } else {
                    lastName.setBackground(getResources().getDrawable(R.drawable.focus_border));
                }


            }else {


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    lastName.setBackgroundDrawable(getResources().getDrawable(R.drawable.view_border));
                } else {
                    lastName.setBackground(getResources().getDrawable(R.drawable.view_border));
                }


            }


        }

        else if(v == password)
        {
            if(hasFocus){

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    password.setBackgroundDrawable(getResources().getDrawable(R.drawable.focus_border));
                } else {
                    password.setBackground(getResources().getDrawable(R.drawable.focus_border));
                }

            }else {


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    password.setBackgroundDrawable(getResources().getDrawable(R.drawable.view_border));
                } else {
                    password.setBackground(getResources().getDrawable(R.drawable.view_border));
                }


            }


        }

        else if(v == email)
        {
            if(hasFocus){


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    email.setBackgroundDrawable(getResources().getDrawable(R.drawable.focus_border));
                } else {
                    email.setBackground(getResources().getDrawable(R.drawable.focus_border));
                }


            }else {


                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    email.setBackgroundDrawable( getResources().getDrawable(R.drawable.view_border) );
                } else {
                    email.setBackground( getResources().getDrawable(R.drawable.view_border));
                }


            }


        }


    }


    public void loadIdentityType(ArrayList<IdentityType> identityTypeArrayList)
    {
        this.identityTypeArrayList = identityTypeArrayList;
        String[] arraySpinner = new String[identityTypeArrayList.size()];
        int i = 0;
        for(IdentityType identityType : identityTypeArrayList)
        {
            arraySpinner[i] = identityType.getName();
            i++;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinner);
        this.identityType.setAdapter(adapter);



    }

    @Override
    public void onClick(View v) {

        if(v == upload)
        {

            showFileChooser();


        }
        else if(v == signUp)
        {

            if(this.firstName.getText().length()==0)
            {
                Toast.makeText(this,"Insert First Name",Toast.LENGTH_SHORT).show();
            }
            else if(this.lastName.getText().length() == 0)
            {
                Toast.makeText(this,"Insert Last Name",Toast.LENGTH_SHORT).show();
            }
            else if(this.email.getText().length() == 0)
            {
                Toast.makeText(this,"Insert Email",Toast.LENGTH_SHORT).show();
            }
            else if(this.password.getText().length() == 0)
            {

                    Toast.makeText(this,"Insert Password",Toast.LENGTH_SHORT).show();
            }
            else if(this.password.getText().length() < 6)
            {
                Toast.makeText(this,"Password Must be 6 character",Toast.LENGTH_SHORT).show();

            }
            else if(this.identityType.getSelectedItem() == null)
            {
                registration.setIdentityType(identityTypeArrayList.get(0));

            }
            else if(!this.upload.getText().equals("uploaded"))
            {
                Toast.makeText(this,"Upload Identity File",Toast.LENGTH_SHORT).show();
            }
            else
            {
                registration.setFirstName(this.firstName.getText().toString());
                registration.setLastName(this.lastName.getText().toString());
                registration.setEmail(this.email.getText().toString());
                registration.setPassword(this.password.getText().toString());


                if(connectivityManagerInfo.isConnectedToInternet() == true)
                {
                    new RegistrationAsyncTask(this,registration).execute();
                }
                else
                {
                    Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }

        }

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
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
                    if(connectivityManagerInfo.isConnectedToInternet())
                        new DocumentAsyncTask(this,path).execute();
                    else
                        Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();


                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void fileUploaded(String token)
    {
        this.upload.setBackgroundDrawable( getResources().getDrawable(R.drawable.dotted_border_selected) );
        this.upload.setText("uploaded");

        registration.setIdentityDocToken(token);

    }

    public void doneRegistration()
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        registration.setIdentityType(identityTypeArrayList.get(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
