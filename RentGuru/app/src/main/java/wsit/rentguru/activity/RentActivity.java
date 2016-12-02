package wsit.rentguru.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import wsit.rentguru.R;
import wsit.rentguru.asynctask.PaymentAsyncTask;
import wsit.rentguru.asynctask.RequestRentAsyncTask;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.Utility;

public class RentActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button startDate,endDate;
    private EditText remarks;
    private Button confirm;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private Calendar myCalendar;
    private int flag;
    private boolean startDateSelected,endDateSelected;
    private ScrollView scrollView;
    private RentRequest rentRequest;
    private int position;
    private RentalProduct rentalProduct;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .merchantName("Reneguru")
            .clientId("AWQr0Ls0qt0zRtXFvSBZ2k3zNgt-0ME5eI6qC8A9dTh2RHodYtDre5cJT7BNElg9mm3dZw6v9F-G-vyn");

    private void initiate()
    {
        this.scrollView = (ScrollView)findViewById(R.id.main_layout_rent_activity);
        myCalendar = Calendar.getInstance();
        this.startDate = (Button)findViewById(R.id.startDate);
        this.startDate.setOnClickListener(this);
        this.endDate = (Button)findViewById(R.id.endDate);
        this.endDate.setOnClickListener(this);
        this.remarks = (EditText)findViewById(R.id.remarks);

        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);

        this.confirm = (Button)findViewById(R.id.confirm_button_rent);
        this.confirm.setOnClickListener(this);

        rentRequest = new RentRequest();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initiate();

        rentalProduct =(RentalProduct) getIntent().getSerializableExtra("rental_product");

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
    }

    @Override
    public void onClick(View v) {

        if(v == startDate)
        {

            flag = 0;
            startDateSelected = true;
            new DatePickerDialog(v.getContext(), this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
        else if(v == endDate)
        {

            flag = 1;
            endDateSelected = true;
            new DatePickerDialog(v.getContext(), this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
        else if(v == confirm)
        {
            if(startDateSelected == false)
            {
                showSnackbar("Please enter start date");

            }
            else if(endDateSelected == false)
            {
                showSnackbar("Please enter end date");

            }
            else
            {
                rentRequest.setRemark(remarks.getText().toString());
                rentRequest.setRentalProduct(rentalProduct);
                if(connectivityManagerInfo.isConnectedToInternet())
                {
                    new RequestRentAsyncTask(this,rentRequest).execute();

                }

            }


        }


    }


    private void updateLabel(Button button) {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        button.setText(sdf.format(myCalendar.getTime()));
        if(flag == 0)
        {
            rentRequest.setStartDate(sdf.format(myCalendar.getTime()));

        }
        else
        {
            rentRequest.setEndDate(sdf.format(myCalendar.getTime()));
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if(flag == 0)
            updateLabel(startDate);
        else
            updateLabel(endDate);

    }

    private void showSnackbar(String message)
    {
        Snackbar snackbar = Snackbar
                .make(scrollView, message, Snackbar.LENGTH_LONG);

        snackbar.show();

    }

    public void payment()
    {

        PayPalPayment payment = new PayPalPayment(new BigDecimal(Utility.rentalProductArrayList.get(position).getRentFee()), "USD", "Rent Item",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);


    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    JSONObject jsonObj = new JSONObject(confirm.toJSONObject().toString());
                    String paymentId = jsonObj.getJSONObject("response").get("id").toString();
                    Log.i("paymentExample", paymentId);

                    // TODO: send 'confirm' to your server for verification.

                    //finish();
                    if(connectivityManagerInfo.isConnectedToInternet())
                        new PaymentAsyncTask(paymentId,this,Utility.requestedItemId).execute();

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }



    public void finishActivity()
    {
        finish();

    }

}
