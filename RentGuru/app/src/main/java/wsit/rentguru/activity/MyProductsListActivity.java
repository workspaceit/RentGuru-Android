package wsit.rentguru.activity;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import wsit.rentguru.R;
import wsit.rentguru.fragment.ApproveProductFragment;
import wsit.rentguru.fragment.UploadedProductFragment;
import wsit.rentguru.model.PostProduct;
import wsit.rentguru.utility.CustomViewPager;
import wsit.rentguru.utility.TabLayoutUtils;

public class MyProductsListActivity extends AppCompatActivity implements View.OnClickListener {

    public static Toolbar toolbar;
    private TabLayout tabLayout;
    public static CustomViewPager viewPager;
    public static PostProduct postProduct;


    private void initiate()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        this.postProduct = new PostProduct();

        TabLayoutUtils.enableTabs(tabLayout, true);


     /*   toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
            }
        });*/



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products_list);
        initiate();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UploadedProductFragment(), "My Product");
        adapter.addFragment(new ApproveProductFragment(), "Rent Request");
       // adapter.addFragment(new PostProductThirdFragment(), "Requested");
        viewPager.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
