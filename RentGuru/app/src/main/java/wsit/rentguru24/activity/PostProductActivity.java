package wsit.rentguru24.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import wsit.rentguru24.R;
import wsit.rentguru24.fragment.PostProductFirstFragment;
import wsit.rentguru24.fragment.PostProductSecondFragment;
import wsit.rentguru24.fragment.PostProductThirdFragment;
import wsit.rentguru24.model.PostProduct;
import wsit.rentguru24.utility.TabLayoutUtils;
import wsit.rentguru24.utility.Utility;

public class PostProductActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    public static PostProduct postProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Utility.temporaryArrayList.clear();
        this.postProduct = new PostProduct();

        TabLayoutUtils.enableTabs(tabLayout, false);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PostProductFirstFragment(), " ");
        adapter.addFragment(new PostProductSecondFragment(), " ");
        adapter.addFragment(new PostProductThirdFragment(), " ");
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
