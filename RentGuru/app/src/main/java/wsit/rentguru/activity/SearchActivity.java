package wsit.rentguru.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.adapter.CategoryAdapter;
import wsit.rentguru.adapter.ColorAdapter;
import wsit.rentguru.adapter.SearchProductGridViewAdapter;
import wsit.rentguru.asynctask.CategoryAsncTask;
import wsit.rentguru.asynctask.GetSearchResultAsynTask;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private Toolbar toolbar;
    private EditText searchtitleText;
    private Spinner categorySpinner,subcategorySpinner;
    private TextView locationPickerTextView;
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
    private boolean categorySelected,subcategorySelected;
    private int parentCategoryPosition=0;
    private ArrayList<CategoryModel>categoryModels=null;
    public static ArrayList <RentalProduct>rentalSearchProducts;
    private SearchProductGridViewAdapter searchProductGridViewAdapter;

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
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        searchButton=(Button)findViewById(R.id.search_submit_button);
        divider=findViewById(R.id.search_divider);
        gridView=(GridView)findViewById(R.id.search_grid_view);
        nestedScrollView=(NestedScrollView)findViewById(R.id.scroller_nested);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.search_swiperefresh);
        appBarLayout=(AppBarLayout)findViewById(R.id.search_app_bar_layout);

        catArr = new String[1];
        catArr[0] = "Select Category";

        categorySpinner.setOnItemSelectedListener(this);
        catAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_category,catArr);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(catAdapter);

        searchButton.setOnClickListener(this);

        searchProductGridViewAdapter=new SearchProductGridViewAdapter(this);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initiate();
        if (connectivityManagerInfo.isConnectedToInternet() == true) {
            new CategoryAsncTask(this).execute();
        }


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
                    subCatArr = new String[this.categoryModels.get(position-1).getSubcategory().size()+1];
                    subCatArr[0] = "Select Sub-Category";
                    CategoryModel[] categoryModels = new CategoryModel[this.categoryModels.get(position-1).getSubcategory().size()];

                    categoryModels = this.categoryModels.get(position-1).getSubcategory().toArray(categoryModels);

                    if(categoryModels.length !=0) {
                        this.subcategorySpinner.setVisibility(View.VISIBLE);
                        subcategorySelected = false;
                        categorySelected = true;
                        for (int i = 0; i < this.categoryModels.get(position - 1).getSubcategory().size(); i++) {
                            subCatArr[i + 1] = categoryModels[i].getName();
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
                        ArrayList<Integer> value = new ArrayList<Integer>();
                        value.add(this.categoryModels.get(position-1).getId());
                        System.out.println("Cat name: "+this.categoryModels.get(position-1).getName());



                    }
                }
                else
                {
                    categorySelected = false;
                    subcategorySelected = false;
                    this.subcategorySpinner.setVisibility(View.GONE);

                }
                break;


            case R.id.search_product_sub_category_spinner:


                if(position!=0) {

                    subcategorySelected = true;
                    ArrayList<Integer>value=new ArrayList<>();
                    value.add(this.categoryModels.get(this.parentCategoryPosition).getSubcategory().get(position-1).getId());
                    System.out.println(this.categoryModels.get(this.parentCategoryPosition).getSubcategory().get(position-1).getName());



                }
                else
                {
                    subcategorySelected = false;

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
            rentalSearchProducts=new ArrayList<>();
            gridView.setAdapter(searchProductGridViewAdapter);
            new GetSearchResultAsynTask(this).execute("5","0","a","","","","");
        }
    }

    public void setRentalProduct(ArrayList<RentalProduct>rentalProduct){
        rentalSearchProducts.addAll(rentalProduct);
        searchProductGridViewAdapter.notifyDataSetChanged();

    }

    }
