package wsit.rentguru.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.Scope;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wsit.rentguru.BuildConfig;
import wsit.rentguru.R;
import wsit.rentguru.asynctask.FacebookLoginAsyncTask;
import wsit.rentguru.asynctask.GoogleLoginAsyncTask;
import wsit.rentguru.asynctask.LoginAsyncTask;
import wsit.rentguru.model.Login;
import wsit.rentguru.utility.ConnectivityManagerInfo;


public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private EditText email,password;
    private Button signIn,signUp,forgotPassword;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private Login login;
    private ImageButton fbLoginButton,googleLoginButton;
    private ImageButton twitterLoginButton;
    private CallbackManager mCallbackManager;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;
    // Google client to communicate with Google
    private GoogleApiClient mGoogleApiClient;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "HCA193eICEQ5H5UICe8nqY8Xu";
    private static final String TWITTER_SECRET = "1L7mJNBCAcXCQzJLxhSUKF4vqZGkqS4oJprCpu8EoggjSEBiQ7";
    private static final String CLIENT_ID = "399870560253-n3arljvb3ajm8qejs554dnpj6rmi6l8v.apps.googleusercontent.com";

    String accountName="example";
    private static final int REQ_SIGN_IN_REQUIRED = 55664;


    private void initiate()
    {

        this.email = (EditText)findViewById(R.id.email);
        this.password = (EditText)findViewById(R.id.password);

        this.email.setOnFocusChangeListener(this);
        this.password.setOnFocusChangeListener(this);

        this.signIn = (Button)findViewById(R.id.signIn);
        this.signIn.setOnClickListener(this);

        this.signUp = (Button)findViewById(R.id.signUp);
        this.signUp.setOnClickListener(this);

        this.fbLoginButton = (ImageButton)findViewById(R.id.fbButton);
        this.fbLoginButton.setOnClickListener(this);



        FacebookSdk.sdkInitialize(this.getApplicationContext());

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        mCallbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        Log.d("access", loginResult.getAccessToken().getToken());
                        LoginManager.getInstance().logOut();

                        if (connectivityManagerInfo.isConnectedToInternet()) {
                            new FacebookLoginAsyncTask(LoginActivity.this, loginResult.getAccessToken().getToken()).execute();
                        }

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(CLIENT_ID)
                .requestServerAuthCode(CLIENT_ID)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        this.googleLoginButton = (ImageButton)findViewById(R.id.googleButton);
        this.googleLoginButton.setOnClickListener(this);


        twitterLoginButton = (ImageButton) findViewById(R.id.twiterButton);
        twitterLoginButton.setOnClickListener(this);

        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);

        this.login = new Login();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initiate();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(v == email)
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



    }

    @Override
    public void onClick(View v) {

        Intent i;

        if(v == signIn)
        {
            String email = this.email.getText().toString();
            CharSequence input = email;
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);

            if(!matcher.matches())
            {
                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();

            }
            else if(this.password.getText().toString().length() <6)
            {
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show();
            }
            else {
                login.setEmail(this.email.getText().toString());
                login.setPassword(this.password.getText().toString());
                login.setType(1);

                if (connectivityManagerInfo.isConnectedToInternet() == true) {
                    new LoginAsyncTask(this,login).execute();
                } else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }

        }
        else if(v == signUp)
        {
            i = new Intent(this,RegistrationActivity.class);
            startActivity(i);

        }
        else if(v == fbLoginButton)
        {
            if(connectivityManagerInfo.isConnectedToInternet()) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));

            }
        }
        else if(v == googleLoginButton)
        {
            try {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }catch (Exception ex)
            {
                Toast.makeText(this,"Please update Google Play Service",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == twitterLoginButton)
        {


        }

    }


    public void doneLogin()
    {

        Intent i =new Intent(this,HomeActivity.class);
        startActivity(i);
        finish();


    }





    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void resolveSignInError() {

        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                System.out.println("surver auth code : " + acct.getServerAuthCode());
                System.out.println("id token: "+acct.getIdToken());

                accountName = acct.getEmail();
                new RetrieveTokenTask().execute(accountName);

            }
        }



    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            // store mConnectionResult
            mConnectionResult = result;

            if (signedInUser) {
                resolveSignInError();
            }
        }

    }


    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e("Error", e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateToken(s);
        }
    }


    public void updateToken(String token)
    {
        System.out.println("token: "+token);
        if(connectivityManagerInfo.isConnectedToInternet())
        {
            new GoogleLoginAsyncTask(LoginActivity.this,token).execute();
        }
    }



}
