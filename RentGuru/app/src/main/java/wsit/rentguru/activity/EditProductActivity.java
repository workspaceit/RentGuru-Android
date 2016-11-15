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
import wsit.rentguru.fragment.EditProductImagesFragment;
import wsit.rentguru.fragment.EditProductInfoFragment;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.utility.CustomViewPager;
import wsit.rentguru.utility.ViewPagerAdapter;

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
        adapter.addFragment(new EditProductImagesFragment(), "Product Images");
        viewPager.setAdapter(adapter);
    }




}
