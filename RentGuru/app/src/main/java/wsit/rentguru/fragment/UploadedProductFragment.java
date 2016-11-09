package wsit.rentguru.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import wsit.rentguru.R;
import wsit.rentguru.activity.PostProductActivity;
import wsit.rentguru.adapter.UploadedProductListAdapter;
import wsit.rentguru.asynctask.UploadedProductListAsyncTask;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;


public class UploadedProductFragment extends Fragment implements ListView.OnScrollListener, View.OnClickListener {

    private View view;

    private SwipeMenuListView uploadedproductListView;
    private ArrayList<MyRentalProduct> rentalProductArrayList;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int offset;
    private UploadedProductListAdapter uploadedProductListAdapter;
    private boolean flag_loading;
    private LinearLayout noProductLayout;
    private Button addNewProduct;
    private boolean moreProduct;

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

        noProductLayout=(LinearLayout)view.findViewById(R.id.no_product_layout);
        addNewProduct=(Button)noProductLayout.findViewById(R.id.add_new_product_button);
        noProductLayout.setVisibility(View.GONE);
        addNewProduct.setOnClickListener(this);
        moreProduct=true;
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
            if(flag_loading == false && moreProduct==true)
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
        System.out.println("size "+rentalProductArrayList.size()+" "+this.rentalProductArrayList.size());

        if (rentalProductArrayList.size()==0){
            moreProduct=false;

            if (this.rentalProductArrayList.size()==0) {
                noProductLayout.setVisibility(View.VISIBLE);
            }
            return;

        }

        if (noProductLayout.getVisibility()==View.VISIBLE)
            noProductLayout.setVisibility(View.GONE);

        for(int i = 0;i<rentalProductArrayList.size();i++)
        {
            this.rentalProductArrayList.add(rentalProductArrayList.get(i));

        }
        this.uploadedProductListAdapter = new UploadedProductListAdapter(getActivity(),this.rentalProductArrayList);
        this.uploadedproductListView.setAdapter(uploadedProductListAdapter);

        offset+=1;
        flag_loading = false;

    }

    @Override
    public void onClick(View v) {
        if (v==addNewProduct){
            Intent i = new Intent(getActivity(),PostProductActivity.class);
            startActivity(i);
        }
    }
}
