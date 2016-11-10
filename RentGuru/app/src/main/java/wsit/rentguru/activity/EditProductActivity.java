package wsit.rentguru.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import wsit.rentguru.R;
import wsit.rentguru.fragment.ApproveProductFragment;
import wsit.rentguru.fragment.EditProductInfoFragment;
import wsit.rentguru.fragment.UploadedProductFragment;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.CustomViewPager;

public class EditProductActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    public static CustomViewPager viewPager;
    public static MyRentalProduct myRentalProduct;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initialize();
    }

    private void initialize(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EditProductInfoFragment(), "Product Info");

        // adapter.addFragment(new PostProductThirdFragment(), "Requested");
        viewPager.setAdapter(adapter);
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
