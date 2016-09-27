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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import wsit.rentguru.R;
import wsit.rentguru.adapter.ProductOtherImagesAdapter;
import wsit.rentguru.asynctask.ApprovalDecisionAsyncTask;
import wsit.rentguru.asynctask.CancelationAsyncTask;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.Utility;

public class RentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private int position;
    private int type,state;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader;
    private RentRequest rentRequest;
    private ArrayList<RentRequest> rentRequestArrayList;
    private TextView contactNumber,email,address,username,startDate,endDate,rentType,productTitle,description,remarksTextview,remarksValue,from,to,rentValue;
    private String fromDate,toDate;
    private ImageView productPicture;
    private View remarksView;
    private RecyclerView otherImages;
    private ProductOtherImagesAdapter productOtherImagesAdapter;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private Button cancel,approve;
    private ImageView userImage;

    private void initialize()
    {
        position = getIntent().getExtras().getInt("position");
        this.rentRequestArrayList = (ArrayList<RentRequest>)getIntent().getSerializableExtra("arrayList");
        this.type = getIntent().getExtras().getInt("type");
        this.state = getIntent().getExtras().getInt("state");

        this.rentRequest = rentRequestArrayList.get(position);

        this.contactNumber = (TextView)findViewById(R.id.contact_number);
        this.email = (TextView)findViewById(R.id.email);
        this.address = (TextView)findViewById(R.id.address);
        this.username = (TextView)findViewById(R.id.user_name);
        this.startDate = (TextView)findViewById(R.id.start_date);
        this.endDate = (TextView)findViewById(R.id.end_date);
        this.rentType = (TextView)findViewById(R.id.rent_type);
        this.rentValue = (TextView)findViewById(R.id.rent_value);

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
        this.remarksView = (View)findViewById(R.id.remarks_view);

        this.from = (TextView)findViewById(R.id.from);
        this.to = (TextView)findViewById(R.id.to);

        this.otherImages = (RecyclerView)findViewById(R.id.select_other_image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.otherImages.setLayoutManager(layoutManager);

        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);

        this.approve = (Button)findViewById(R.id.approve);
        this.approve.setOnClickListener(this);
        this.cancel = (Button)findViewById(R.id.cancel);
        this.cancel.setOnClickListener(this);

        this.userImage = (ImageView)findViewById(R.id.profilePic);
    }


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

        if(type==1) {
            this.email.setText(this.rentRequest.getRequestedBy().getEmail());
            this.address.setText(this.rentRequest.getRequestedBy().getUser().getUserAddress().getAddress());
            String username = this.rentRequest.getRequestedBy().getUser().getFirstName() + " " + this.rentRequest.getRequestedBy().getUser().getLastName();
            System.out.println(username);
            this.username.setText(username);
            this.imageLoader.displayImage(Utility.picUrl + this.rentRequest.getRequestedBy().getUser().getProfilePicture().getOriginal().getPath(), userImage, displayImageOptions);

            if(state==1 || state == 2)
            {
                this.cancel.setVisibility(View.GONE);
                this.approve.setVisibility(View.GONE);
            }

        }
        else
        {
            this.email.setText(this.rentRequest.getRentalProduct().getOwner().getEmail());
            this.address.setText(this.rentRequest.getRentalProduct().getOwner().getUser().getUserAddress().getAddress());
            String username = this.rentRequest.getRentalProduct().getOwner().getUser().getFirstName() + " " + this.rentRequest.getRentalProduct().getOwner().getUser().getLastName();
            System.out.println(username);
            this.username.setText(username);
            this.approve.setVisibility(View.GONE);
            this.imageLoader.displayImage(Utility.picUrl + this.rentRequest.getRentalProduct().getOwner().getUser().getProfilePicture().getOriginal().getPath(), userImage, displayImageOptions);

            if(state==1 || state == 2)
            {
                this.cancel.setVisibility(View.GONE);
                this.approve.setVisibility(View.GONE);
            }
        }
        this.startDate.setText(this.rentRequest.getStartDate());
        this.endDate.setText(this.rentRequest.getEndDate());
        this.rentType.setText("Rent/" + this.rentRequest.getRentalProduct().getRentType().getName());
        this.rentValue.setText("$"+String.valueOf(this.rentRequest.getRentalProduct().getRentFee()));

        this.imageLoader.displayImage(Utility.picUrl + this.rentRequest.getRentalProduct().getProfileImage().getOriginal().getPath(), productPicture, displayImageOptions);
        this.productTitle.setText(this.rentRequest.getRentalProduct().getName());
        this.description.setText(this.rentRequest.getRentalProduct().getDescription());

        if(this.rentRequest.getRemark().length()==0)
        {
            this.remarksValue.setVisibility(View.GONE);
            this.remarksView.setVisibility(View.GONE);
            this.remarksTextview.setVisibility(View.GONE);

        }
        else
        {
            this.remarksValue.setText(this.rentRequest.getRemark());

        }


        this.from.setText(getDate(this.rentRequest.getRentalProduct().getAvailableFrom()));
        this.to.setText(getDate(this.rentRequest.getRentalProduct().getAvailableTill()));


        this.productOtherImagesAdapter = new ProductOtherImagesAdapter(this.rentRequest.getRentalProduct().getOtherImages(), this);
        this.otherImages.setAdapter(this.productOtherImagesAdapter);

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

    private void calculateDifference(String start,String end)
    {



    }

    public void onApprove() {

        setResult(RESULT_OK, null);
        finish();

    }

    public void onCancelation() {

        setResult(RESULT_OK, null);
        finish();

    }


    @Override
    public void onClick(View v) {

        if(v == approve)
        {
            if(connectivityManagerInfo.isConnectedToInternet())
            new ApprovalDecisionAsyncTask(this, 1, this.rentRequest.getId()).execute();
        }
        else if(v == cancel)
        {
            if(connectivityManagerInfo.isConnectedToInternet()) {

             if(type==1)
                new ApprovalDecisionAsyncTask(this, 2, this.rentRequest.getId()).execute();
             else
                 new CancelationAsyncTask(this,this.rentRequest.getId()).execute();
            }
        }

    }
}
