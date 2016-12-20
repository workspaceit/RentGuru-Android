package wsit.rentguru24.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import wsit.rentguru24.R;
import wsit.rentguru24.adapter.CategoryExpandableListViewAdapter;
import wsit.rentguru24.asynctask.CategoryAsncTask;
import wsit.rentguru24.model.CategoryModel;
import wsit.rentguru24.utility.AnimatedExpandableListView;
import wsit.rentguru24.utility.ConnectivityManagerInfo;

public class CategoryActivity extends AppCompatActivity implements  ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener{

    private Toolbar toolbar;
    private static ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    private ConnectivityManagerInfo connectivityManagerInfo;
    private AnimatedExpandableListView expandableListView;
    private CategoryExpandableListViewAdapter categoryExpandableListViewAdapter;



    private void initialize(){
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        connectivityManagerInfo=new ConnectivityManagerInfo(this);
        expandableListView=(AnimatedExpandableListView)findViewById(R.id.category_expandable_list_view);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnChildClickListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initialize();
        if (connectivityManagerInfo.isConnectedToInternet() == true) {

            if (CategoryActivity.categoryModels.size()==0) {
                new CategoryAsncTask(this).execute();
            }else {
                setData(CategoryActivity.categoryModels);
            }
        }

    }

    public void setData(ArrayList<CategoryModel> categoryModels){
        CategoryActivity.categoryModels=categoryModels;
        this.categoryExpandableListViewAdapter=new CategoryExpandableListViewAdapter(this,CategoryActivity.categoryModels);
        this.expandableListView.setAdapter(categoryExpandableListViewAdapter);


    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (CategoryActivity.categoryModels.get(groupPosition).getSubcategory().isEmpty()) {
            Intent intent=new Intent(this,CategoryProductListViewActivity.class);
            intent.putExtra("category_id",CategoryActivity.categoryModels.get(groupPosition).getId());
            intent.putExtra("category_title",CategoryActivity.categoryModels.get(groupPosition).getName());
            startActivity(intent);

        } else {
            expandableListView.setSelection(groupPosition);
            if (expandableListView.isGroupExpanded(groupPosition)) {

                expandableListView.collapseGroupWithAnimation(groupPosition);
            } else {
                expandableListView.expandGroupWithAnimation(groupPosition);

            }

        }
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent=new Intent(this,CategoryProductListViewActivity.class);
        intent.putExtra("category_id",CategoryActivity.categoryModels.get(groupPosition).getSubcategory().get(childPosition).getId());
        intent.putExtra("category_title",CategoryActivity.categoryModels.get(groupPosition).getSubcategory().get(childPosition).getName());
        startActivity(intent);
        return true;
    }


}
