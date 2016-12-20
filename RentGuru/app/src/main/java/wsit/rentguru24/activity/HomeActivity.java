package wsit.rentguru24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

import wsit.rentguru24.R;
import wsit.rentguru24.adapter.StaggeredAdapter;
import wsit.rentguru24.asynctask.AccessTokenAsyncTask;
import wsit.rentguru24.asynctask.GetBannerImageAsynTask;
import wsit.rentguru24.asynctask.ProductListAsyncTask;
import wsit.rentguru24.model.BannerImage;
import wsit.rentguru24.model.Login;
import wsit.rentguru24.utility.ConnectivityManagerInfo;
import wsit.rentguru24.preference.SessionManager;
import wsit.rentguru24.utility.ShowNotification;
import wsit.rentguru24.utility.Utility;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AbsListView.OnScrollListener, AbsListView.OnItemClickListener, NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout parentLayout;
    private static final String TAG = "StaggeredGridActivity";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    private StaggeredGridView staggeredGridView;
    private boolean mHasRequestedMore;
    private StaggeredAdapter staggeredAdapter;

    public static ArrayList<String> productsUrlList;
    private NestedScrollView scrollView;
    private View divider;
    private AppBarLayout appBarLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SessionManager sessionManager;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private SliderLayout sliderShow;



    private void initiate()
    {
        View child,child1,child2;
        this.parentLayout = (LinearLayout) findViewById(R.id.featured_product_layout);
        child = getLayoutInflater().inflate(R.layout.child_layout, null);
        this.parentLayout.addView(child);
        child1 = getLayoutInflater().inflate(R.layout.child_layout, null);
        this.parentLayout.addView(child1);
        child2 = getLayoutInflater().inflate(R.layout.child_layout, null);
        this.parentLayout.addView(child2);

        this.productsUrlList = new ArrayList<String>();
        staggeredGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        staggeredAdapter = new StaggeredAdapter(this,android.R.layout.simple_list_item_1, this.productsUrlList);



        this.divider =findViewById(R.id.divider);
        this.scrollView = (NestedScrollView)findViewById(R.id.scroller);
        this.scrollView.setOnScrollChangeListener(this);


        this.appBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        this.mSwipeRefreshLayout.setColorSchemeResources(
                R.color.loading);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.sessionManager = new SessionManager(this);

        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);
        this.sliderShow = (SliderLayout) findViewById(R.id.slider);
        bannerImageDownLoad();
        authenticate();


    }


    private void bannerImageDownLoad(){
        if(connectivityManagerInfo.isConnectedToInternet()){
            if (Utility.bannerImages.size()>0){
                setupBannerImage();
            }else {
                new GetBannerImageAsynTask(this).execute();
            }
        }

    }

    public void setupBannerImage(){
        sliderShow.removeAllSliders();
        if (Utility.bannerImages.size() >= 1) {
            for (BannerImage banner : Utility.bannerImages) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(Utility.bannerUrl + banner.getImagePath())
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                sliderShow.addSlider(textSliderView);
            }
        } else {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("Banner not found")
                    .image(R.drawable.image_not_found)
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            sliderShow.addSlider(textSliderView);
        }
    }

    private void authenticate()
    {

        if(sessionManager.isLoggedIn()== false)
            sessionManager.createLoginSession(Utility.authCredential.getAccesstoken());
        else
        {
            Login login = new Login();
            login.setType(2);
            login.setAccessToken(sessionManager.getUserDetails());

            if (connectivityManagerInfo.isConnectedToInternet() == true) {
                new AccessTokenAsyncTask(this,login).execute();
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }


    }



    public ArrayList<String> generateData() {


        staggeredAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;


        return productsUrlList;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initiate();


        if (savedInstanceState != null) {
            productsUrlList = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
        }

        if (productsUrlList == null) {
            productsUrlList = generateData();
        }

        for (String product : productsUrlList) {
            staggeredAdapter.add(product);
        }

        staggeredGridView.setAdapter(staggeredAdapter);
        staggeredGridView.setOnScrollListener(this);
        staggeredGridView.setOnItemClickListener(this);

        this.staggeredGridView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (scrollView.getVisibility()==View.VISIBLE) {
                    appBarLayout.setExpanded(true, true);
                    divider.setVisibility(View.GONE);

                    scrollView.animate().alpha(0.0f);
                    scrollView.setVisibility(View.GONE);
                }

                return false;
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent mIntent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_search)
        {
            mIntent = new Intent(this,SearchActivity.class);
            startActivity(mIntent);
        }else if (id==R.id.action_category){
            mIntent=new Intent(this,CategoryActivity.class);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_post) {

            Intent i = new Intent(this,PostProductActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_profile) {

            Intent i = new Intent(this,MyProductsListActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_requested_products) {

            Intent i = new Intent(this,MyBookingActivity.class);
            startActivity(i);

        } else if (id==R.id.nav_sign_out){
            ShowNotification.logoutDailog(this,sessionManager,"Logout","Confirm Logout?");

        }else if (id==R.id.nav_my_pay_pal){
            Intent intent=new Intent(this,PaypalAccountSettingsActivity.class);
            startActivity(intent);
        }else if (id==R.id.nav_edit_profile){
            Intent i=new Intent(this,EditProfileActivity.class);
            startActivity(i);

        }else if (id==R.id.nav_rent_request){
            Intent i=new Intent(this, RentRequestActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, productsUrlList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,ProductDetailsActivity.class);
        i.putExtra("position", position);
        i.putExtra("callFlag",1);
        Utility.productPosition = position;
        startActivity(i);


    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        Log.d(TAG, "onScrollStateChanged:" + scrollState);

    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d(TAG, "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
                //onLoadMoreItems();

                if(connectivityManagerInfo.isConnectedToInternet() && Utility.indicator== false)
                {
                    new ProductListAsyncTask(this,Utility.offset).execute();

                }


            }
        }
    }

    private void onLoadMoreItems() {

        final ArrayList<String> sampleData = generateData();
        for (String data : sampleData) {
            staggeredAdapter.add(data);
        }
        // stash all the data in our backing store
        productsUrlList.addAll(sampleData);
        // notify the adapter that we can update now
        staggeredAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;
    }



    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        Log.d("scroll:", "called");
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        // if diff is zero, then the bottom has been reached


            if (diff == 0) {
                // do stuff


            }


        }


    @Override
    public void onRefresh() {

        Log.i("refresh", "onRefresh called from SwipeRefreshLayout");

        // This method performs the actual data-refresh operation.
        // The method calls setRefreshing(false) when it's finished.
         myUpdateOperation();


    }

    private void myUpdateOperation()
    {
        this.scrollView.animate().alpha(0.0f);
        this.scrollView.setVisibility(View.VISIBLE);
        this.divider.setVisibility(View.GONE);
        this.mSwipeRefreshLayout.setRefreshing(false);

    }


    public void doneLogin()
    {
        if(connectivityManagerInfo.isConnectedToInternet())
        {
           // new ProductListAsyncTask(this,Utility.offset).execute();

        }


    }


    public void closeActivity()
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }


}



