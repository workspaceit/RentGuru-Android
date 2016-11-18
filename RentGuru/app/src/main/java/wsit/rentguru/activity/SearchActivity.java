package wsit.rentguru.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.adapter.SearchProductGridViewAdapter;
import wsit.rentguru.asynctask.CategoryAsncTask;
import wsit.rentguru.asynctask.GetSearchResultAsynTask;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, SeekBar.OnSeekBarChangeListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {


    private Toolbar toolbar;
    private EditText searchtitleText;
    private Spinner categorySpinner,subcategorySpinner;
    private TextView locationPickerTextView,seekBarTextView;
    private SeekBar seekBar;
    private Button searchButton;
    private View divider;
    private GridView gridView;
    private NestedScrollView nestedScrollView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private ConnectivityManagerInfo connectivityManagerInfo;

    private String[] catArr;
    private String[] subCatArr;

    private ArrayAdapter<String> catAdapter;
    private ArrayAdapter<String> subCatAdapter;
    private boolean categorySelected,subcategorySelected,moreSearchProduct,loadingProduct;

    private ArrayList<CategoryModel>categoryModels=null;
    public static ArrayList <RentalProduct>rentalSearchProducts;
    private SearchProductGridViewAdapter searchProductGridViewAdapter;
    private int seekProgrees, categoryId,limit,offset,parentCategoryPosition;;;
    private GoogleApiClient mClient;
    private static final int PLACE_PICKER_REQUEST = 1000;
    private Place place;
    private StringBuilder formHeader;
    private boolean firstTime;


    private void initiate()
    {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connectivityManagerInfo=new ConnectivityManagerInfo(this);

        searchtitleText=(EditText)findViewById(R.id.seach_title_text_view);
        categorySpinner=(Spinner)findViewById(R.id.search_product_category_spinner);
        subcategorySpinner=(Spinner)findViewById(R.id.search_product_sub_category_spinner);
        locationPickerTextView=(TextView)findViewById(R.id.location_picker_text_view);
        subcategorySpinner.setVisibility(View.GONE);
        seekBar=(SeekBar) findViewById(R.id.seekbar);
        searchButton=(Button)findViewById(R.id.search_submit_button);
        divider=findViewById(R.id.search_divider);
        gridView=(GridView)findViewById(R.id.search_grid_view);
        nestedScrollView=(NestedScrollView)findViewById(R.id.scroller_nested);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.search_swiperefresh);
        appBarLayout=(AppBarLayout)findViewById(R.id.search_app_bar_layout);
        seekBarTextView=(TextView)findViewById(R.id.seek_bar_progreess_text);

        catArr = new String[1];
        catArr[0] = "Select Category";

        categorySpinner.setOnItemSelectedListener(this);
        subcategorySpinner.setOnItemSelectedListener(this);
        catAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_category,catArr);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(catAdapter);

        searchButton.setOnClickListener(this);

        searchProductGridViewAdapter=new SearchProductGridViewAdapter(this);

        this.swipeRefreshLayout.setColorSchemeResources(
                R.color.loading);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        rentalSearchProducts=new ArrayList<>();
        seekBar.setOnSeekBarChangeListener(this);
        locationPickerTextView.setOnClickListener(this);
        categoryId=0;
        parentCategoryPosition=0;
        formHeader=new StringBuilder();
        limit=6;
        offset=0;

        gridView.setOnScrollListener(this);
        gridView.setOnItemClickListener(this);
        moreSearchProduct=false;
        loadingProduct=false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initiate();
        if (connectivityManagerInfo.isConnectedToInternet() == true) {
            new CategoryAsncTask(this).execute();
        }



        //buildGoogleApiClient();

        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                System.out.println(rentalSearchProducts.size()+" size");
                if (nestedScrollView.getVisibility()==View.VISIBLE && rentalSearchProducts.size()>0) {
                    appBarLayout.setExpanded(false);
                    divider.setVisibility(View.GONE);
                    nestedScrollView.animate().alpha(0.0f);
                    nestedScrollView.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }




    public void setData(ArrayList<CategoryModel>categoryModels){
        this.categoryModels=categoryModels;
        catArr = new String[this.categoryModels.size()+1];

        catArr[0] = "Select Category";

        for(int i =0; i<this.categoryModels.size();i++)
        {

            catArr[i+1] = this.categoryModels.get(i).getName();

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_category,catArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.categorySpinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId())
        {
            case R.id.search_product_category_spinner:
                Log.d("here: ",String.valueOf(position));
                if(position!=0)
                {
                    this.categoryId=this.categoryModels.get(position-1).getId();
                    subCatArr = new String[this.categoryModels.get(position-1).getSubcategory().size()+1];
                    subCatArr[0] = "Select Sub-Category";
                    CategoryModel[] childCategoryModels = new CategoryModel[this.categoryModels.get(position-1).getSubcategory().size()];

                    childCategoryModels = this.categoryModels.get(position-1).getSubcategory().toArray(childCategoryModels);

                    if(childCategoryModels.length !=0) {
                        this.subcategorySpinner.setVisibility(View.VISIBLE);
                        subcategorySelected = false;
                        categorySelected = true;
                        for (int i = 0; i < this.categoryModels.get(position - 1).getSubcategory().size(); i++) {
                            subCatArr[i + 1] = childCategoryModels[i].getName();
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item_category, subCatArr);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subcategorySpinner.setAdapter(adapter);
                        this.parentCategoryPosition=position-1;
                        System.out.println(this.categoryModels.get(this.parentCategoryPosition).getName());
                    }
                    else
                    {
                        categorySelected = true;
                        subcategorySelected = true;
                        this.subcategorySpinner.setVisibility(View.GONE);





                    }
                }
                else
                {
                    categorySelected = false;
                    subcategorySelected = false;
                    this.subcategorySpinner.setVisibility(View.GONE);
                    this.categoryId=0;

                }
                break;


            case R.id.search_product_sub_category_spinner:


                if(position!=0) {

                    subcategorySelected = true;


                    this.categoryId=this.categoryModels.get(this.parentCategoryPosition).getSubcategory().get(position-1).getId();


                }
                else
                {
                    subcategorySelected = false;
                    this.categoryId=this.categoryModels.get(this.parentCategoryPosition).getId();

                }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId())
        {
            case R.id.search_product_category_spinner:

                categorySelected = false;

                break;


            case R.id.search_product_sub_category_spinner:

                if(categorySelected == true)
                    subcategorySelected = false;

                break;

        }
    }

    @Override
    public void onClick(View v) {
        if (v==searchButton){


            if (place!=null && seekProgrees==0){
                ShowNotification.makeToast(this,"You Must Select Distance");
                return;
            }

            StringBuilder stringBuilder=new StringBuilder();
            formHeader.setLength(0);
            String title=searchtitleText.getText().toString();
            boolean flag=false;


            if(!title.equals("")){
                flag=true;
                stringBuilder.append("title="+title);

            }

            if(categoryId!=0){
                if (flag==true){
                    stringBuilder.append("&categoryId="+categoryId);
                }else {
                    stringBuilder.append("categoryId="+categoryId);
                    flag=true;
                }
            }

            if (seekProgrees!=0){
                if (flag==true){
                    stringBuilder.append("&radius="+seekProgrees);
                }else {
                    stringBuilder.append("radius="+seekProgrees);
                    flag=true;
                }
            }

            if (place!=null){
                if (flag==true){
                    stringBuilder.append("&lat="+place.getLatLng().latitude+"&lng="+place.getLatLng().longitude);
                }else {
                    stringBuilder.append("lat="+place.getLatLng().latitude+"&lng="+place.getLatLng().longitude);
                    flag=true;
                }
            }
                if (flag==true){
                    if (rentalSearchProducts!=null && rentalSearchProducts.size()>0){
                        rentalSearchProducts.clear();
                    }

                    formHeader=stringBuilder;
                    limit=5;
                    offset=0;
                    gridView.setAdapter(searchProductGridViewAdapter);
                    searchProductGridViewAdapter.notifyDataSetChanged();
                    if (connectivityManagerInfo.isConnectedToInternet() == true) {
                        new GetSearchResultAsynTask(this, formHeader.toString()).execute(String.valueOf(limit), String.valueOf(offset));
                    }
                    moreSearchProduct=true;
                    loadingProduct=false;
                    firstTime=true;
                 System.out.println(formHeader.toString());
                }else {
                    ShowNotification.makeToast(this,"You have Nothing to Search");
                }



        }else if (v==locationPickerTextView){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        }

    }

    public void setRentalProduct(ArrayList<RentalProduct>rentalProduct){
        if (rentalProduct!=null) {
            rentalSearchProducts.addAll(rentalProduct);
            searchProductGridViewAdapter.notifyDataSetChanged();
            loadingProduct=false;
            firstTime=false;
        }else {
            if (firstTime==true){
              ShowNotification.showSnacksBarLong(this,swipeRefreshLayout,"Found Nothing...");
                firstTime=!firstTime;
            }
            moreSearchProduct=false;
        }

    }

    @Override
    public void onRefresh() {
        System.out.println("Called from swipe refresh layout");
        myUpdateOperation();

    }

    private void myUpdateOperation()
    {
        if (nestedScrollView.getVisibility()==View.GONE) {
            this.nestedScrollView.animate().alpha(0.0f);
            this.nestedScrollView.setVisibility(View.VISIBLE);
            this.appBarLayout.setExpanded(true);
            this.divider.setVisibility(View.GONE);
            this.swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (this.seekBar==seekBar){
            this.seekProgrees=progress;
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) this.seekBar.getLayoutParams();
            float seekBarX;
            int[] location = new int[2];
            seekBarX = location[0];

            seekBarTextView.setText("" + progress + " km");
            float x = seekBarX
                    + this.seekBar.getThumb().getBounds().centerX()
                    - seekBarTextView.getWidth()/2
                    + p.leftMargin;
            x=x+10;
            seekBarTextView.setX(x);

        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                this.place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String address = String.format("%s", place.getAddress());

                stBuilder.append(placename);
                stBuilder.append("\n");

                stBuilder.append(address);

                locationPickerTextView.setText(stBuilder.toString());


            }else {

                locationPickerTextView.setText("You Didn't Pick Any Location");

            }

        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            if (firstVisibleItem+visibleItemCount>=totalItemCount && moreSearchProduct && !loadingProduct ) {

                loadingProduct = true;

                if(connectivityManagerInfo.isConnectedToInternet())
                {
                    offset++;
                    new GetSearchResultAsynTask(this,formHeader.toString()).execute(String.valueOf(limit),String.valueOf(offset));

                }



        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent i = new Intent(this,ProductDetailsActivity.class);
            i.putExtra("position", position);
            i.putExtra("callFlag",2);
            Utility.productPosition = position;
            startActivity(i);

    }
}
