package wsit.rentguru24.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import wsit.rentguru24.R;
import wsit.rentguru24.adapter.CategoryProductListGridViewAdapter;
import wsit.rentguru24.asynctask.GetRentalProductCategoryWiseAsynTask;
import wsit.rentguru24.model.RentalProduct;
import wsit.rentguru24.utility.ConnectivityManagerInfo;
import wsit.rentguru24.utility.ShowNotification;

public class CategoryProductListViewActivity extends AppCompatActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private int categoryId;
    private String categoryName;
    private TextView categoryTileTextView;
    private GridView gridView;
    public static ArrayList<RentalProduct>categoryRentalProdutcs;
    private CategoryProductListGridViewAdapter categoryProductListGridViewAdapter;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int limit,offset;
    private boolean moreProduct,loadingProduct;

    private void initialize(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRentalProdutcs=new ArrayList<>();
        categoryTileTextView=(TextView)findViewById(R.id.category_title_text);
        categoryTileTextView.setText(categoryName);
        gridView=(GridView)findViewById(R.id.category_grid_view);
        connectivityManagerInfo=new ConnectivityManagerInfo(this);
        moreProduct=true;
        loadingProduct=false;
        limit=6;
        offset=0;
        categoryProductListGridViewAdapter=new CategoryProductListGridViewAdapter(this);
        gridView.setAdapter(categoryProductListGridViewAdapter);
        gridView.setOnScrollListener(this);
        gridView.setOnItemClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product_list_view);
        this.categoryId=getIntent().getIntExtra("category_id",0);
        this.categoryName=getIntent().getStringExtra("category_title");
        initialize();

        System.out.println(categoryId+" "+categoryName);



    }


    public void setData(ArrayList<RentalProduct> rentalProducts){
        if (rentalProducts.size()==0){
            moreProduct=false;
            if (CategoryProductListViewActivity.categoryRentalProdutcs.size()==0){
                ShowNotification.showSnacksBarLong(this,gridView,"No Product...");
            }

            return;
        }


        CategoryProductListViewActivity.categoryRentalProdutcs.addAll(rentalProducts);

        offset++;
        categoryProductListGridViewAdapter.notifyDataSetChanged();

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem+visibleItemCount>=totalItemCount && moreProduct && !loadingProduct ) {

            loadingProduct = true;

            if(connectivityManagerInfo.isConnectedToInternet())
            {

                new GetRentalProductCategoryWiseAsynTask(this,categoryId,offset,limit).execute();

            }



        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this,ProductDetailsActivity.class);
        i.putExtra("position", position);
        i.putExtra("callFlag",3);
        startActivity(i);
    }
}
