package wsit.rentguru.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import wsit.rentguru.R;
import wsit.rentguru.preference.SessionManager;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;

public class StartupActivity extends AppCompatActivity {

    ConnectivityManagerInfo connectivityManagerInfo;



    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        connectivityManagerInfo=new ConnectivityManagerInfo(this);



        session = new SessionManager(getApplicationContext());
        int DELAY = 1500;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (session.checkLogin() == true) {
                    Intent intent = new Intent(StartupActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        }, DELAY);


    }
}
