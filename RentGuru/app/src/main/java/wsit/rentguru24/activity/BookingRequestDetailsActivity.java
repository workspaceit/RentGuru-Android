package wsit.rentguru24.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import wsit.rentguru24.R;
import wsit.rentguru24.adapter.ProductOtherImagesAdapter;
import wsit.rentguru24.asynctask.GetRentInformationAsynTask;
import wsit.rentguru24.asynctask.ProductBookingAsynTask;
import wsit.rentguru24.model.RentInf;
import wsit.rentguru24.model.RentRequest;
import wsit.rentguru24.utility.ConnectivityManagerInfo;
import wsit.rentguru24.utility.RentFeesHelper;
import wsit.rentguru24.utility.ShowNotification;
import wsit.rentguru24.utility.Utility;

public class BookingRequestDetailsActivity extends AppCompatActivity implements View.OnClickListener{


    private int type;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader;
    private RentRequest rentRequest;

    private TextView contactNumber, email, address, username, startDate, endDate, rentType, productTitle, description,
            remarksTextview, remarksValue, from, to, rentValue, numberOfDaysTextView, productCurrentValueTextView, totalRentTextView,
            totalDepositAmountTextView, totalTextView;
    private String fromDate, toDate;
    private ImageView productPicture;
    private View remarksView;
    private RecyclerView otherImages;
    private ProductOtherImagesAdapter productOtherImagesAdapter;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private Button cancelButton,retunProductButon;
    private ImageView userImage;
    private RentInf rentInf;
    private ScrollView contentRentDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_request_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();

    }



    private void initialize() {

        this.rentRequest = (RentRequest) getIntent().getSerializableExtra("rent_request");
        type = getIntent().getIntExtra("type", 0);
        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);


        this.contactNumber = (TextView) findViewById(R.id.contact_number);
        this.email = (TextView) findViewById(R.id.email);
        this.address = (TextView) findViewById(R.id.address);
        this.username = (TextView) findViewById(R.id.user_name);
        this.startDate = (TextView) findViewById(R.id.start_date);
        this.endDate = (TextView) findViewById(R.id.end_date);
        this.rentType = (TextView) findViewById(R.id.rent_type);
        this.rentValue = (TextView) findViewById(R.id.rent_value);
        this.cancelButton=(Button)findViewById(R.id.cancel_button);
        this.retunProductButon=(Button)findViewById(R.id.return_product_button);
        cancelButton.setOnClickListener(this);
        retunProductButon.setOnClickListener(this);


        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        this.displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        this.productPicture = (ImageView) findViewById(R.id.product_image);
        this.productTitle = (TextView) findViewById(R.id.product_title);
        this.description = (TextView) findViewById(R.id.product_description_value);
        this.remarksTextview = (TextView) findViewById(R.id.remarks_textview);
        this.remarksValue = (TextView) findViewById(R.id.remarks_value);
        this.remarksView = findViewById(R.id.remarks_view);

        this.from = (TextView) findViewById(R.id.from);
        this.to = (TextView) findViewById(R.id.to);

        this.otherImages = (RecyclerView) findViewById(R.id.select_other_image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.otherImages.setLayoutManager(layoutManager);
        rentType.setText("Rent/" + this.rentRequest.getRentalProduct().getRentType().getName());
        this.remarksValue.setText(this.rentRequest.getRemark());
        this.contentRentDetails = (ScrollView) findViewById(R.id.content_rent_details);




        this.userImage = (ImageView) findViewById(R.id.profilePic);
        this.numberOfDaysTextView = (TextView) findViewById(R.id.no_of_days);

        productCurrentValueTextView = (TextView) findViewById(R.id.rent_current_value);
        totalDepositAmountTextView = (TextView) findViewById(R.id.total_deposit_amount);
        totalRentTextView = (TextView) findViewById(R.id.total_rent);
        totalTextView = (TextView) findViewById(R.id.total_text_view);

        imageLoader.displayImage(Utility.profileImageUrl + rentRequest.getRequestedBy().getUser().getProfilePicture().getOriginal().getPath(),
                this.userImage);


        this.username.setText(rentRequest.getRequestedBy().getUser().getFirstName() + " " + rentRequest.getRequestedBy().getUser().getLastName());
        this.email.setText(rentRequest.getRequestedBy().getEmail());
        this.address.setText(rentRequest.getRequestedBy().getUser().getUserAddress().getAddress());
        this.startDate.setText(getformatedDate(this.rentRequest.getStartDate()));
        this.endDate.setText(getformatedDate(this.rentRequest.getEndDate()));
        this.numberOfDaysTextView.setText(calculateDifference(this.rentRequest.getStartDate(), this.rentRequest.getEndDate()));
        this.rentValue.setText(Utility.CURRENCY + " " + this.rentRequest.getRentalProduct().getRentFee());
        this.productCurrentValueTextView.setText(Utility.CURRENCY + " " + rentRequest.getRentalProduct().getCurrentValue());

        this.imageLoader.displayImage(Utility.picUrl + this.rentRequest.getRentalProduct().getProfileImage().getOriginal().getPath(),
                this.productPicture);
        this.productTitle.setText(this.rentRequest.getRentalProduct().getName());
        this.from.setText(getDate(this.rentRequest.getRentalProduct().getAvailableFrom()));
        this.to.setText(getDate(this.rentRequest.getRentalProduct().getAvailableTill()));
        this.description.setText(this.rentRequest.getRentalProduct().getDescription());
        this.productOtherImagesAdapter = new ProductOtherImagesAdapter(this.rentRequest.getRentalProduct().getOtherImages(), this);
        this.otherImages.setAdapter(this.productOtherImagesAdapter);




        totalDepositAmountTextView.setText(Utility.CURRENCY + " " + this.rentRequest.getRentalProduct().getCurrentValue());
        totalTextView.setText(Utility.CURRENCY + " " + this.rentRequest.getRentalProduct().getCurrentValue());


        if (type == 0) {
           cancelButton.setVisibility(View.VISIBLE);

        } else if (type == 1) {
            if (connectivityManagerInfo.isConnectedToInternet()) {
                new GetRentInformationAsynTask(this, rentRequest.getId()).execute();
            }

        } else if (type == 2) {

        }


        this.totalRentTextView.setText(Utility.CURRENCY + " " + getRentFee(rentRequest.getStartDate(), rentRequest.getEndDate()));


    }


    private String getDate(String timeStamp) {
        String date = "";
        long time = Long.valueOf(timeStamp).longValue();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        date = DateFormat.format("MMM dd yyyy", cal).toString();

        return date;
    }

    private String getformatedDate(String s) {
        System.out.println(s);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(sdf1.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return (new SimpleDateFormat("MMM").format(cal.getTime())) + " " + cal.get(Calendar.DAY_OF_MONTH) + " , " + cal.get(Calendar.YEAR);
    }

    private String calculateDifference(String start, String end) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        try {
            startCal.setTime(sdf1.parse(start));
            endCal.setTime(sdf1.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long endDateTimeInMillis = startCal.getTimeInMillis();
        long startDateTimeInMillis = endCal.getTimeInMillis();

        return String.valueOf(TimeUnit.MILLISECONDS.toDays(Math.abs(endDateTimeInMillis - startDateTimeInMillis)));

    }

    private String getRentFee(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null, endDate = null;
        try {
            dateStart = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double amount = RentFeesHelper.getRentFee(rentRequest.getRentalProduct().getRentType().getId(),
                rentRequest.getRentalProduct().getRentFee(), dateStart, endDate);

        return String.valueOf(new DecimalFormat(".##").format(amount));

    }

    public void completeGetRentInf(RentInf rentInf){

        if (rentInf==null){
            ShowNotification.makeToast(this,"Network Error");
            return;
        }

        this.rentInf=rentInf;

        if (this.rentInf.getRentalProductReturned().getId()>0) {
            this.retunProductButon.setVisibility(View.GONE);
        }else{
            this.retunProductButon.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        if (v==cancelButton){
            if (connectivityManagerInfo.isConnectedToInternet())
                new ProductBookingAsynTask(this,this.rentRequest.getId(),1).execute();
            else
                ShowNotification.makeToast(this,"No Internet connection");


        }else if (v==retunProductButon){
            if (connectivityManagerInfo.isConnectedToInternet())
                new ProductBookingAsynTask(this,this.rentInf.getId(),2).execute();
            else
                ShowNotification.makeToast(this,"No Internet connection");
        }

    }


    public void cancelComplete(boolean flag){
        if (flag){
            cancelButton.setVisibility(View.GONE);
            ShowNotification.showSnacksBarLong(this,contentRentDetails,"Booking is canceled successfully");
        }else {
            ShowNotification.showSnacksBarLong(this,contentRentDetails,"Network Error");
        }
    }

    public void retunProductComplete(boolean flag){
        if (flag){
            retunProductButon.setVisibility(View.GONE);
            ShowNotification.showSnacksBarLong(this,contentRentDetails,"Your request sent successfully");


    }else {
        ShowNotification.showSnacksBarLong(this,contentRentDetails,"Network Error");
    }

    }
}
