package wsit.rentguru.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;


import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.adapter.CategoryAdapter;
import wsit.rentguru.adapter.ColorAdapter;

public class SearchActivity extends AppCompatActivity {


    private Button search;
    private RecyclerView selectCategory,selectColor;
    private CategoryAdapter cAdapter;
    private ColorAdapter colorAdapter;
    private Toolbar toolbar;

    private void initiate()
    {
        this.search = (Button)findViewById(R.id.search_button);
        this.selectCategory = (RecyclerView)findViewById(R.id.select_category);
        this.selectCategory.setHasFixedSize(true);

        this.selectColor = (RecyclerView)findViewById(R.id.select_color);
        this.selectColor.setHasFixedSize(true);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        this.selectCategory.setLayoutManager(layoutManager);

        String[] mTest = new String[4];
        mTest[0] = "one";
        mTest[1] = "Two";
        mTest[2] = "three";
        mTest[3] = "four";

        cAdapter = new CategoryAdapter(mTest);
        selectCategory.setAdapter(cAdapter);



        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        this.selectColor.setLayoutManager(layoutManager1);

        String[] mTest1 = new String[6];
        mTest1[0] = "#FA8072";
        mTest1[1] = "#CD5C5C";
        mTest1[2] = "#FFA07A";
        mTest1[3] = "#0000FF";
        mTest1[4] = "#800000";
        mTest1[5] = "#00FFFF";

        colorAdapter = new ColorAdapter(mTest1);
        selectColor.setAdapter(colorAdapter);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        initiate();
    }



    private ArrayList<String> generateData() {
        ArrayList<String> listData = new ArrayList<String>();
        listData.add("http://i62.tinypic.com/2iitkhx.jpg");
        listData.add("http://i61.tinypic.com/w0omeb.jpg");
        listData.add("http://i60.tinypic.com/w9iu1d.jpg");
        listData.add("http://i60.tinypic.com/iw6kh2.jpg");

        return listData;
    }

}
