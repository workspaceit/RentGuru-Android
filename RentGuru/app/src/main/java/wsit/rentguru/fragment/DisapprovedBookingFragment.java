package wsit.rentguru.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.activity.BookingRequestDetailsActivity;
import wsit.rentguru.adapter.RentRequestProductListAdapter;
import wsit.rentguru.asynctask.RequestedProductsListAsyncTask;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisapprovedBookingFragment extends Fragment implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private View view;
    private SwipeMenuListView disapprovedListView;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private RentRequestProductListAdapter approveProductListAdapter;
    public ArrayList<RentRequest> rentRequestArrayList;
    private boolean load_flag;
    private final int STATE=2;
    private int offset;


    public DisapprovedBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_disapproved_booking, container, false);
        disapprovedListView =(SwipeMenuListView)view.findViewById(R.id.pending_product_list);
        this.rentRequestArrayList=new ArrayList<>();
        approveProductListAdapter=new RentRequestProductListAdapter(getActivity(),this.rentRequestArrayList);
        disapprovedListView.setAdapter(approveProductListAdapter);
        connectivityManagerInfo=new ConnectivityManagerInfo(getActivity());
        load_flag=false;
        offset=0;
        disapprovedListView.setOnScrollListener(this);
        disapprovedListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        load_flag=true;
        offset=0;

        if (connectivityManagerInfo.isConnectedToInternet())
            new RequestedProductsListAsyncTask(this,offset,STATE).execute();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(firstVisibleItem+visibleItemCount>=totalItemCount && !load_flag && totalItemCount!=0){
            load_flag=true;
            if (connectivityManagerInfo.isConnectedToInternet())
                new RequestedProductsListAsyncTask(this,offset,STATE).execute();

        }

    }


    public void completeLoading(ArrayList<RentRequest> rentRequests){

        if (offset==0)
            this.rentRequestArrayList.clear();

        this.rentRequestArrayList.addAll(rentRequests);


        approveProductListAdapter.setArray(this.rentRequestArrayList);
        approveProductListAdapter.notifyDataSetChanged();
        this.offset++;
        load_flag=false;

    }

    public void noData(){
        if (offset==0) {
            this.rentRequestArrayList.clear();
            approveProductListAdapter.setArray(this.rentRequestArrayList);
            approveProductListAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.rentRequestArrayList.size()>0) {
            Intent intent = new Intent(getActivity(), BookingRequestDetailsActivity.class);
            intent.putExtra("rent_request", this.rentRequestArrayList.get(position));
            intent.putExtra("type", STATE);
            startActivity(intent);
        }
    }


}
