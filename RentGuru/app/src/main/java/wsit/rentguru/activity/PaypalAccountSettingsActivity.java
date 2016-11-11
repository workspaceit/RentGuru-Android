package wsit.rentguru.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wsit.rentguru.R;
import wsit.rentguru.asynctask.GetPayPalEmailAsynTask;
import wsit.rentguru.asynctask.SetPayPalEmailAsynTask;
import wsit.rentguru.model.UserPaypalCredential;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;

public class PaypalAccountSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private EditText editText;
    private Button button;
    private View root;
    private UserPaypalCredential userPaypalCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_account_settings);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        connectivityManagerInfo=new ConnectivityManagerInfo(this);
        editText=(EditText)findViewById(R.id.paypal_email_edit_text);
        button=(Button)findViewById(R.id.paypal_save_button);
        root=findViewById(R.id.activity_paypal_account_settings);
        button.setOnClickListener(this);

        if (connectivityManagerInfo.isConnectedToInternet()){
            new GetPayPalEmailAsynTask(this).execute();
        }
    }

    public void setEmail(UserPaypalCredential userPaypalCredential){
        this.userPaypalCredential=userPaypalCredential;

        if (userPaypalCredential==null){
            this.button.setVisibility(View.GONE);
           showSnackbar("Something went wrong..");
        }else if (userPaypalCredential.getEmail().equals("")){
            if (button.getVisibility()==View.GONE){
                button.setVisibility(View.VISIBLE);
            }
           showSnackbar("No PayPal Email..");
        }else {
            editText.setText(userPaypalCredential.getEmail());
            if (button.getVisibility()==View.GONE){
                button.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v==button){
            if (verifyInput()){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
                new SetPayPalEmailAsynTask(this).execute(editText.getText().toString());
            }
        }
    }


    private boolean verifyInput(){
        String email = this.editText.getText().toString();
        CharSequence input = email;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            ShowNotification.makeToast(this, "Please Enter a valid Email ID");
            this.editText.requestFocus();
            return false;
        }else if (editText.getText().toString().equals(userPaypalCredential.getEmail())){
            ShowNotification.makeToast(this,"No Change in Email");
            this.editText.requestFocus();
            return false;
        }

        return true;
    }

    public void chnageEmail(boolean flag){
        if (flag){
          showSnackbar("Your Paypal Email Changed Successfully");
        }else {
            showSnackbar("Something went wrong...");
        }
    }


    private void showSnackbar(String msg){
        final Snackbar snackBar = Snackbar.make(root, msg, Snackbar.LENGTH_INDEFINITE);

        snackBar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
}
