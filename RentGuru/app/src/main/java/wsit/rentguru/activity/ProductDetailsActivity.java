package wsit.rentguru.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import wsit.rentguru.R;
import wsit.rentguru.adapter.ProductOtherImagesAdapter;
import wsit.rentguru.asynctask.RatingAsncTask;
import wsit.rentguru.model.Product;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.RecyclerItemClickListener;
import wsit.rentguru.utility.Utility;

public class ProductDetailsActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener{

    private RatingBar ratingBar;
    private String ratingValue;
    private ProductOtherImagesAdapter productOtherImagesAdapter;
    private RentalProduct rentalProductDetails;
    private RecyclerView productOtherImages;
    private ImageView originalImage;
    private ImageLoader imageLoader;

    private TextView rentFee,availableTime,categoryName,overView,overViewText,productTitle,productLocationTextView;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private ImageView likeProduct;
    private Button rentNow;
    private DisplayImageOptions displayImageOptions;
    private static int position,flag;
    private static boolean status;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                }
            });


        try {
            if (getIntent().getExtras()!=null){
                position = getIntent().getExtras().getInt("position");
                flag=getIntent().getExtras().getInt("callFlag");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }


        if (flag==1){
            this.rentalProductDetails = Utility.rentalProductArrayList.get(position);

        }else if (flag==2){
            this.rentalProductDetails=SearchActivity.rentalSearchProducts.get(position);
        }else if (flag==3){
            this.rentalProductDetails=CategoryProductListViewActivity.categoryRentalProdutcs.get(position);
        }



        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        this.displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        this.ratingBar = (RatingBar) findViewById(R.id.rating);
        this.ratingBar.setRating(this.rentalProductDetails.getAverageRating());
        //this.ratingBar.setOnRatingBarChangeListener(this);
        ratingBar.setIsIndicator(true);
        this.productTitle = (TextView) findViewById(R.id.product_title);

        this.rentFee = (TextView) findViewById(R.id.rent_fee);
        this.availableTime = (TextView) findViewById(R.id.available_time);
        this.categoryName = (TextView) findViewById(R.id.category_name);
        this.overView = (TextView) findViewById(R.id.overview);
        this.productLocationTextView=(TextView)findViewById(R.id.product_location);


        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.productOtherImages = (RecyclerView) findViewById(R.id.select_other_image);
        this.productOtherImages.setLayoutManager(layoutManager);
        this.productOtherImages.setHasFixedSize(true);
        if (status == false) {
            this.rentalProductDetails.getOtherImages().add(this.rentalProductDetails.getProfileImage());
            Collections.reverse(this.rentalProductDetails.getOtherImages());
            status = true;
        }
        this.productOtherImagesAdapter = new ProductOtherImagesAdapter(this.rentalProductDetails.getOtherImages(), this);
        this.productOtherImages.setAdapter(this.productOtherImagesAdapter);




        this.productOtherImages.addOnItemTouchListener(
                new RecyclerItemClickListener(this, this.productOtherImages, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        imageLoader.displayImage(Utility.picUrl + rentalProductDetails.getOtherImages().get(position).getOriginal().getPath(), originalImage,displayImageOptions);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );




        if(this.rentalProductDetails.getOtherImages().size()>1) {
            System.out.println(rentalProductDetails.getOtherImages().size());
            this.productOtherImages.setVisibility(View.VISIBLE);

        }
        else
        {
            this.productOtherImages.setVisibility(View.GONE);
        }

        this.originalImage = (ImageView)findViewById(R.id.product_picture);
        this.imageLoader.displayImage(Utility.picUrl + this.rentalProductDetails.getProfileImage().getOriginal().getPath(), this.originalImage,displayImageOptions);


        this.rentFee.setText("$"+String.valueOf(rentalProductDetails.getCurrentValue())+"/"+rentalProductDetails.getRentType().getName());
        this.productLocationTextView.setText(rentalProductDetails.getProductLocation().getFormattedAddress());

        String fromDate = getDate(rentalProductDetails.getAvailableFrom());
        String toDate = getDate(rentalProductDetails.getAvailableTill());

        this.availableTime.setText(String.valueOf(fromDate+" to "+toDate));

        String categoyValue = "";
        for(int i = 0;i < rentalProductDetails.getProductCategories().size();i++)
        {
            if(i==0)
            categoyValue =  String.valueOf(rentalProductDetails.getProductCategories().get(i).getCategory().getName());
            else
                categoyValue = categoyValue + ", " + String.valueOf(rentalProductDetails.getProductCategories().get(i).getCategory().getName());
        }

        this.categoryName.setText(categoyValue);
        this.overViewText = (TextView)findViewById(R.id.overview_text);

        if(rentalProductDetails.getDescription()!="")
        this.overView.setText(rentalProductDetails.getDescription());
        else
        {
            this.overView.setVisibility(View.GONE);
            this.overViewText.setVisibility(View.GONE);


        }

        this.productTitle.setText(rentalProductDetails.getName());



        this.rentNow = (Button)findViewById(R.id.rent_now_button);
        this.rentNow.setOnClickListener(this);

    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

      ratingValue = String.valueOf(rating);
        Log.d("rating", ratingValue);

        if(connectivityManagerInfo.isConnectedToInternet())
        {
            new RatingAsncTask(this,rating,this.rentalProductDetails.getId()).execute();

        }


    }


    @Override
    public void onClick(View v) {
        if(v==rentNow){
            Intent i = new Intent(this,RentActivity.class);
            i.putExtra("rental_product", rentalProductDetails);
            startActivity(i);
        }




    }

    public void updateRating(boolean response)
    {
        if(response == false)
        {
            Toast.makeText(this,"You have already rated the item",Toast.LENGTH_SHORT).show();

        }


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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.rentalProductDetails.getOtherImages().remove(0);
        status = false;


    }
}
