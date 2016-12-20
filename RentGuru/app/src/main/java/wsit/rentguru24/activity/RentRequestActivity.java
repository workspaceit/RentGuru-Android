package wsit.rentguru24.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import wsit.rentguru24.R;
import wsit.rentguru24.fragment.ApprovedRentRequestFragment;
import wsit.rentguru24.fragment.DisapprovedRentRequestFragment;
import wsit.rentguru24.fragment.PendingRentRequestFragment;
import wsit.rentguru24.utility.CustomViewPager;
import wsit.rentguru24.utility.TabLayoutUtils;
import wsit.rentguru24.utility.ViewPagerAdapter;

public class RentRequestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_request);
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

        TabLayoutUtils.enableTabs(tabLayout, true);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingRentRequestFragment(), "Pending");
        adapter.addFragment(new ApprovedRentRequestFragment(),"Approved");
        adapter.addFragment(new DisapprovedRentRequestFragment(),"Disapproved");

        viewPager.setAdapter(adapter);
    }
}
