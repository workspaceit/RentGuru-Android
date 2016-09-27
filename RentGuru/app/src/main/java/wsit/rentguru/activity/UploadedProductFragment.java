package wsit.rentguru.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import wsit.rentguru.R;
import wsit.rentguru.adapter.UploadedProductListAdapter;
import wsit.rentguru.asynctask.UploadedProductListAsyncTask;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;


public class UploadedProductFragment extends Fragment implements ListView.OnScrollListener{

    private View view;

    private SwipeMenuListView uploadedproductListView;
    private ArrayList<MyRentalProduct> rentalProductArrayList;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int offset;
    private UploadedProductListAdapter uploadedProductListAdapter;
    private boolean flag_loading;

    public UploadedProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_uploaded_product, container, false);
        uploadedproductListView = (SwipeMenuListView)view.findViewById(R.id.uploaded_product_list);
        uploadedproductListView.setOnScrollListener(this);
        this.rentalProductArrayList = new ArrayList<MyRentalProduct>();
        this.connectivityManagerInfo = new ConnectivityManagerInfo(this.getContext());
        this.offset = 0;



        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(getContext().getApplicationContext());
                // set item background
                editItem.setBackground(R.color.colorAccent);
                // set item width
                editItem.setWidth(100);
                editItem.setIcon(R.drawable.ic_product_edit);



                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext().getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.delete_color);
                // set item width
                deleteItem.setWidth(100);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };


        uploadedproductListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        uploadedproductListView.setMenuCreator(creator);

        if(connectivityManagerInfo.isConnectedToInternet())
        {
            new UploadedProductListAsyncTask(this,offset).execute();
        }



        uploadedproductListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        System.out.println("edit clicked");
                        break;
                    case 1:
                        // delete
                        System.out.println("delete clicked");
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });



        return view;
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
                    new UploadedProductListAsyncTask(this,offset).execute();
                }

            }
        }


    }


    public void onDatatload(ArrayList<MyRentalProduct>rentalProductArrayList)
    {
        for(int i = 0;i<rentalProductArrayList.size();i++)
        {
            this.rentalProductArrayList.add(rentalProductArrayList.get(i));

        }
        this.uploadedProductListAdapter = new UploadedProductListAdapter(this.getContext(),this.rentalProductArrayList);
        this.uploadedproductListView.setAdapter(uploadedProductListAdapter);

        offset+=1;
        flag_loading = false;

    }
}
