package wsit.rentguru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.sql.SQLData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import wsit.rentguru.R;
import wsit.rentguru.adapter.ProductOtherImagesAdapter;
import wsit.rentguru.asynctask.ApprovalDecisionAsyncTask;
import wsit.rentguru.asynctask.CancelationAsyncTask;
import wsit.rentguru.asynctask.GetRentInformationAsynTask;
import wsit.rentguru.model.RentInf;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.RentFeesHelper;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;

public class RentRequestOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private int position;
    private int type,state;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader;
    private RentRequest rentRequest;

    private TextView contactNumber,email,address,username,startDate,endDate,rentType,productTitle,description,
            remarksTextview,remarksValue,from,to,rentValue,numberOfDaysTextView,productCurrentValueTextView,totalRentTextView,
            totalDepositAmountTextView,totalTextView;
    private String fromDate,toDate;
    private ImageView productPicture;
    private View remarksView;
    private RecyclerView otherImages;
    private ProductOtherImagesAdapter productOtherImagesAdapter;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private Button cancel,approve,requesToReturnButton,disputeButton,confirmReturnButton;
    private ImageView userImage;
    private RentInf rentInf;
    private LinearLayout returnRequestLayout,firstButtonLayout;
    private ScrollView contentRentDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();

        fromDate = "";
        toDate = "";


    }




    private void initialize()
    {

        this.rentRequest = (RentRequest) getIntent().getSerializableExtra("rent_request");
        type=getIntent().getIntExtra("type",0);
        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);





        this.contactNumber = (TextView)findViewById(R.id.contact_number);
        this.email = (TextView)findViewById(R.id.email);
        this.address = (TextView)findViewById(R.id.address);
        this.username = (TextView)findViewById(R.id.user_name);
        this.startDate = (TextView)findViewById(R.id.start_date);
        this.endDate = (TextView)findViewById(R.id.end_date);
        this.rentType = (TextView)findViewById(R.id.rent_type);
        this.rentValue = (TextView)findViewById(R.id.rent_value);
        disputeButton=(Button)findViewById(R.id.dispute_button);
        disputeButton.setOnClickListener(this);
        confirmReturnButton=(Button)findViewById(R.id.confirm_return_button);
        confirmReturnButton.setOnClickListener(this);

        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        this.displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        this.productPicture = (ImageView)findViewById(R.id.product_image);
        this.productTitle = (TextView)findViewById(R.id.product_title);
        this.description = (TextView)findViewById(R.id.product_description_value);
        this.remarksTextview = (TextView)findViewById(R.id.remarks_textview);
        this.remarksValue = (TextView)findViewById(R.id.remarks_value);
        this.remarksView = findViewById(R.id.remarks_view);

        this.from = (TextView)findViewById(R.id.from);
        this.to = (TextView)findViewById(R.id.to);

        this.otherImages = (RecyclerView)findViewById(R.id.select_other_image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.otherImages.setLayoutManager(layoutManager);
        rentType.setText("Rent/"+this.rentRequest.getRentalProduct().getRentType().getName());
        this.remarksValue.setText(this.rentRequest.getRemark());
        this.contentRentDetails=(ScrollView)findViewById(R.id.content_rent_details);





        this.approve = (Button)findViewById(R.id.approve);
        this.approve.setOnClickListener(this);
        this.cancel = (Button)findViewById(R.id.cancel);
        this.cancel.setOnClickListener(this);

        this.userImage = (ImageView)findViewById(R.id.profilePic);
        this.numberOfDaysTextView=(TextView)findViewById(R.id.no_of_days);

        productCurrentValueTextView=(TextView)findViewById(R.id.rent_current_value);
        totalDepositAmountTextView=(TextView)findViewById(R.id.total_deposit_amount);
        totalRentTextView=(TextView)findViewById(R.id.total_rent);
        totalTextView=(TextView)findViewById(R.id.total_text_view);

        imageLoader.displayImage(Utility.profileImageUrl+rentRequest.getRequestedBy().getUser().getProfilePicture().getOriginal().getPath(),
                this.userImage);





        this.username.setText(rentRequest.getRequestedBy().getUser().getFirstName()+" "+rentRequest.getRequestedBy().getUser().getLastName());
        this.email.setText(rentRequest.getRequestedBy().getEmail());
        this.address.setText(rentRequest.getRequestedBy().getUser().getUserAddress().getAddress());
       this.startDate.setText(getformatedDate(this.rentRequest.getStartDate()));
        this.endDate.setText(getformatedDate(this.rentRequest.getEndDate()));
        this.numberOfDaysTextView.setText(calculateDifference(this.rentRequest.getStartDate(),this.rentRequest.getEndDate()));
        this.rentValue.setText(Utility.CURRENCY+" "+this.rentRequest.getRentalProduct().getRentFee());
        this.productCurrentValueTextView.setText(Utility.CURRENCY+" "+rentRequest.getRentalProduct().getCurrentValue());

        this.imageLoader.displayImage(Utility.picUrl+this.rentRequest.getRentalProduct().getProfileImage().getOriginal().getPath(),
                this.productPicture);
        this.productTitle.setText(this.rentRequest.getRentalProduct().getName());
        this.from.setText(getDate(this.rentRequest.getRentalProduct().getAvailableFrom()));
        this.to.setText(getDate(this.rentRequest.getRentalProduct().getAvailableTill()));
        this.description.setText(this.rentRequest.getRentalProduct().getDescription());
        this.productOtherImagesAdapter = new ProductOtherImagesAdapter(this.rentRequest.getRentalProduct().getOtherImages(), this);
        this.otherImages.setAdapter(this.productOtherImagesAdapter);
        this.requesToReturnButton=(Button)findViewById(R.id.request_to_return_button);
        this.returnRequestLayout=(LinearLayout)findViewById(R.id.return_request_layout);
        this.firstButtonLayout=(LinearLayout)findViewById(R.id.firstButtonLayout);
        totalDepositAmountTextView.setText(Utility.CURRENCY+" "+this.rentRequest.getRentalProduct().getCurrentValue());
        totalTextView.setText(Utility.CURRENCY+" "+this.rentRequest.getRentalProduct().getCurrentValue());


        if (type==0){
            requesToReturnButton.setVisibility(View.GONE);
            returnRequestLayout.setVisibility(View.GONE);
        }else if (type==1){
            firstButtonLayout.setVisibility(View.GONE);
            returnRequestLayout.setVisibility(View.GONE);
            requesToReturnButton.setVisibility(View.GONE);
            if (connectivityManagerInfo.isConnectedToInternet()){
                new GetRentInformationAsynTask(this,rentRequest.getId()).execute();
            }

        }else if (type==2){
            firstButtonLayout.setVisibility(View.GONE);
            requesToReturnButton.setVisibility(View.GONE);
            returnRequestLayout.setVisibility(View.GONE);
        }


        this.totalRentTextView.setText(Utility.CURRENCY+" "+getRentFee(rentRequest.getStartDate(),rentRequest.getEndDate()));


    }



    private String getRentFee(String start,String end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart=null,endDate=null;
        try {
            dateStart=sdf.parse(start);
            endDate=sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double amount= RentFeesHelper.getRentFee(rentRequest.getRentalProduct().getRentType().getId(),
                rentRequest.getRentalProduct().getRentFee(),dateStart,endDate);

        return String.valueOf(amount);

    }


    private String getDate(String timeStamp)
    {
        String date = "";
        long time = Long.valueOf(timeStamp).longValue();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        date = DateFormat.format("MMM dd yyyy", cal).toString();

        return date;
    }

    private String getformatedDate(String s){
        System.out.println(s);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(sdf1.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return (new SimpleDateFormat("MMM").format(cal.getTime()))+" "+cal.get(Calendar.DAY_OF_MONTH)+" , "+cal.get(Calendar.YEAR);
    }

    private String calculateDifference(String start,String end)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCal=Calendar.getInstance();
        Calendar endCal=Calendar.getInstance();

        try {
            startCal.setTime(sdf1.parse(start));
            endCal.setTime(sdf1.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long endDateTimeInMillis = startCal.getTimeInMillis();
        long startDateTimeInMillis = endCal.getTimeInMillis();

        return String.valueOf( TimeUnit.MILLISECONDS.toDays(Math.abs(endDateTimeInMillis - startDateTimeInMillis)));

    }

    public void completeGetRentInf(RentInf rentInf){
        if (rentInf==null){
            ShowNotification.makeToast(this,"Network Error");

        }else{
            this.rentInf=rentInf;


            if (this.rentInf.getRentalProductReturnRequest().getId()>0){
                this.returnRequestLayout.setVisibility(View.GONE);
                this.requesToReturnButton.setVisibility(View.GONE);
                return;
            }

            if (this.rentInf.getRentalProductReturned().getId()>0){
                if (this.rentInf.getRentalProductReturned().isConfirm()==false && this.rentInf.getRentalProductReturned().isDispute()==false){
                    this.returnRequestLayout.setVisibility(View.VISIBLE);
                    this.requesToReturnButton.setVisibility(View.GONE);
                }else {
                    this.returnRequestLayout.setVisibility(View.GONE);
                    this.requesToReturnButton.setVisibility(View.VISIBLE);
                }

                return;

            }

            this.returnRequestLayout.setVisibility(View.GONE);
            this.requesToReturnButton.setVisibility(View.VISIBLE);



        }
    }

    public void onApprove(boolean flag) {
        if (flag==true) {
            ShowNotification.showSnacksBarLong(this, contentRentDetails, "Rent request is approved");
            firstButtonLayout.setVisibility(View.GONE);
            requesToReturnButton.setVisibility(View.VISIBLE);

        }
        else{
            ShowNotification.showSnacksBarLong(this,contentRentDetails,"Network Error");
        }



    }

    public void onCancelation(boolean flag) {

        if (flag==true) {
            ShowNotification.showSnacksBarLong(this, contentRentDetails, "Rent request is canceled");
            firstButtonLayout.setVisibility(View.GONE);
            requesToReturnButton.setVisibility(View.VISIBLE);

        }
        else{
            ShowNotification.showSnacksBarLong(this,contentRentDetails,"Network Error");
        }

    }


    @Override
    public void onClick(View v) {

        if (v == approve) {
            if (connectivityManagerInfo.isConnectedToInternet())
                new ApprovalDecisionAsyncTask(this, this.rentRequest.getId()).execute();
        } else if (v == cancel) {
            if (connectivityManagerInfo.isConnectedToInternet()) {

                new CancelationAsyncTask(this, this.rentRequest.getId()).execute();

            }

        }else if (v==requesToReturnButton){

        }else if (v==disputeButton){

        }else if (v==confirmReturnButton){

        }

    }
}
