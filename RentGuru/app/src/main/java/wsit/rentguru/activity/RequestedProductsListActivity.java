package wsit.rentguru.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.adapter.ApproveProductListAdapter;
import wsit.rentguru.asynctask.CancelationAsyncTask;
import wsit.rentguru.asynctask.RequestedProductsListAsyncTask;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;

public class RequestedProductsListActivity extends AppCompatActivity  implements View.OnClickListener,ListView.OnScrollListener, AdapterView.OnItemClickListener {

    public static Toolbar toolbar;
    private SwipeMenuListView rentSwipeMenuListView;
    private boolean flag_loading;
    private int offset;
    private ArrayList<RentRequest> rentRequestArrayList;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private Button pending,approved,disapproved;
    private int state;
    private SwipeMenuCreator creator;
    private ApproveProductListAdapter requestedProductListAdapter;
    private int arrPosition;
    public static final int REQUEST_CODE = 1;

    private void initiate()
    {

        pending = (Button) findViewById(R.id.pending);
        pending.setOnClickListener(this);
        pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));

        approved = (Button) findViewById(R.id.approved);
        approved.setOnClickListener(this);
        approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));

        disapproved = (Button) findViewById(R.id.disapproved);
        disapproved.setOnClickListener(this);
        disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));

        rentSwipeMenuListView = (SwipeMenuListView)findViewById(R.id.requested_product_list);
        rentSwipeMenuListView.setOnScrollListener(this);
        rentSwipeMenuListView.setOnItemClickListener(this);

        this.rentRequestArrayList = new ArrayList<RentRequest>();
        this.connectivityManagerInfo = new ConnectivityManagerInfo(this);
        this.offset = 0;


        if(state == 0) {
            creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {


                    // create "delete" item
                    SwipeMenuItem cancelItem = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    cancelItem.setBackground(R.color.delete_color);
                    // set item width
                    cancelItem.setWidth(100);
                    // set a icon
                    cancelItem.setIcon(R.drawable.ic_cancel);
                    // add to menu
                    menu.addMenuItem(cancelItem);
                }
            };


            rentSwipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            rentSwipeMenuListView.setMenuCreator(creator);
        }



        if(connectivityManagerInfo.isConnectedToInternet())
        {
            if(state == 0)
                new RequestedProductsListAsyncTask(this,offset,0).execute();
            else if(state == 1)
                new RequestedProductsListAsyncTask(this,offset,1).execute();
            else
                new RequestedProductsListAsyncTask(this,offset,2).execute();
        }



        rentSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        System.out.println("approve clicked");
                        arrPosition = position;
                        getConfirmation(1);

                        break;
                    case 1:
                        // delete
                        System.out.println("disapprove clicked");
                        arrPosition = position;
                        getConfirmation(2);

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_products_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        initiate();

    }


    @Override
    public void onClick(View v) {


        rentRequestArrayList.clear();
        requestedProductListAdapter.notifyDataSetChanged();
        if(v == pending)
        {

            pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));
            approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            state = 0;

            creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {

                    SwipeMenuItem cancelItem = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    cancelItem.setBackground(R.color.delete_color);
                    // set item width
                    cancelItem.setWidth(100);
                    // set a icon
                    cancelItem.setIcon(R.drawable.ic_cancel);
                    // add to menu
                    menu.addMenuItem(cancelItem);
                }
            };


            rentSwipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            rentSwipeMenuListView.setMenuCreator(creator);

            offset = 0;
            if(connectivityManagerInfo.isConnectedToInternet())
                new RequestedProductsListAsyncTask(this,offset,0).execute();



        }
        else if( v == approved)
        {


            pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));
            disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            state = 1;
            rentSwipeMenuListView.setSwipeDirection(SwipeMenuListView.GONE);
            offset = 0;
            if(connectivityManagerInfo.isConnectedToInternet())
                new RequestedProductsListAsyncTask(this,offset,1).execute();

        }
        else if(v == disapproved)
        {
            pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));
            state = 1;
            rentSwipeMenuListView.setSwipeDirection(SwipeMenuListView.GONE);
            offset = 0;
            if(connectivityManagerInfo.isConnectedToInternet())
                new RequestedProductsListAsyncTask(this,offset,2).execute();

        }



    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        Log.i("Scrolling", "1st fragment");

        if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
        {
            if(flag_loading == false)
            {
                flag_loading = true;
                if(connectivityManagerInfo.isConnectedToInternet())
                {

                    if(state == 0)
                        new RequestedProductsListAsyncTask(this,offset,0).execute();
                    else if(state == 1)
                        new RequestedProductsListAsyncTask(this,offset,1).execute();
                    else
                        new RequestedProductsListAsyncTask(this,offset,2).execute();
                }

            }
        }


    }



    private void getConfirmation(int type)
    {
        final int cat = type;
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        if (connectivityManagerInfo.isConnectedToInternet()) {

                            new CancelationAsyncTask(RequestedProductsListActivity.this,rentRequestArrayList.get(arrPosition).getId()).execute();


                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



    }



    public void onDatatload(ArrayList<RentRequest>rentRequestArrayList)
    {
        if(offset == 0)
        {
            this.rentRequestArrayList.clear();
        }

        for(int i = 0;i<rentRequestArrayList.size();i++)
        {
            this.rentRequestArrayList.add(rentRequestArrayList.get(i));

        }
        this.requestedProductListAdapter = new ApproveProductListAdapter(this,this.rentRequestArrayList);
        this.rentSwipeMenuListView.setAdapter(requestedProductListAdapter);

        offset+=1;
        flag_loading = false;

    }




    public void onCancelation() {

        this.rentRequestArrayList.remove(arrPosition);
        requestedProductListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("clicked");
        if(state == 0) {
            Intent intent = new Intent(this, RentDetailsActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("arrayList", this.rentRequestArrayList);
            intent.putExtra("type", 2);
            intent.putExtra("state",state);
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            System.out.println("onActivityResult");

            if (resultCode == RESULT_OK) {
                System.out.println("ok_called");
                Intent refresh = new Intent(this, RequestedProductsListActivity.class);
                startActivity(refresh);
                this.finish();
            }
        } catch (Exception ex) {


        }

    }



}
