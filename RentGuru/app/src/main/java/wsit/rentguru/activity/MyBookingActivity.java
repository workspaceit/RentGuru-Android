package wsit.rentguru.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import wsit.rentguru.R;
import wsit.rentguru.fragment.ApprovedBookingFragment;
import wsit.rentguru.fragment.ApprovedRentRequestFragment;
import wsit.rentguru.fragment.DisapprovedBookingFragment;
import wsit.rentguru.fragment.DisapprovedRentRequestFragment;
import wsit.rentguru.fragment.PendingBookingRequestFragment;
import wsit.rentguru.fragment.PendingRentRequestFragment;
import wsit.rentguru.utility.CustomViewPager;
import wsit.rentguru.utility.TabLayoutUtils;
import wsit.rentguru.utility.ViewPagerAdapter;

public class MyBookingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
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
        adapter.addFragment(new PendingBookingRequestFragment(), "Pending");
        adapter.addFragment(new ApprovedBookingFragment(),"Approved");
        adapter.addFragment(new DisapprovedBookingFragment(),"Disapproved");

        viewPager.setAdapter(adapter);
    }

}
