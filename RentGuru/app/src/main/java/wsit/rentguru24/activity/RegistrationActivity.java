package wsit.rentguru24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wsit.rentguru24.R;
import wsit.rentguru24.asynctask.RegistrationAsyncTask;
import wsit.rentguru24.model.Registration;
import wsit.rentguru24.utility.ConnectivityManagerInfo;

public class RegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener{

    private EditText firstName,lastName,email,password;

    private Button signUp;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private static final int FILE_SELECT_CODE = 0;
    private Registration registration;



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




        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);




        this.registration = new Registration();



        this.signUp = (Button)findViewById(R.id.signUp);
        this.signUp.setOnClickListener(this);



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initiate();




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










    @Override
    public void onClick(View v) {

      if(v == signUp)
        {

            String email = this.email.getText().toString();
            CharSequence input = email;
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if(this.firstName.getText().length()==0)
            {
                Toast.makeText(this,"Insert First Name",Toast.LENGTH_SHORT).show();
            }
            else if(this.lastName.getText().length() == 0)
            {
                Toast.makeText(this,"Insert Last Name",Toast.LENGTH_SHORT).show();
            }
            else if(!matcher.matches())
            {
                Toast.makeText(this,"Email is not valid",Toast.LENGTH_SHORT).show();
            }
            else if(this.password.getText().length() == 0)
            {

                    Toast.makeText(this,"Insert Password",Toast.LENGTH_SHORT).show();
            }
            else if(this.password.getText().length() < 6)
            {
                Toast.makeText(this,"Password Must be 6 character",Toast.LENGTH_SHORT).show();

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









    public void doneRegistration()
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();

    }









}
