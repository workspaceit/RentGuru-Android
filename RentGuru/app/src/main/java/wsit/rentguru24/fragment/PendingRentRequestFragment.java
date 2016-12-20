package wsit.rentguru24.fragment;


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

import wsit.rentguru24.R;
import wsit.rentguru24.activity.BookingRequestDetailsActivity;
import wsit.rentguru24.adapter.RentRequestProductListAdapter;
import wsit.rentguru24.asynctask.ApprovalProductListAsyncTask;
import wsit.rentguru24.model.RentRequest;
import wsit.rentguru24.utility.ConnectivityManagerInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingRentRequestFragment extends Fragment implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private View view;
    private SwipeMenuListView pendingListView;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private RentRequestProductListAdapter approveProductListAdapter;
    public ArrayList<RentRequest> rentRequestArrayList;
    private boolean load_flag;
    private final int STATE=0;
    private int offset;



    public PendingRentRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_pending_rent_request, container, false);
        pendingListView=(SwipeMenuListView)view.findViewById(R.id.pending_product_list);
        this.rentRequestArrayList=new ArrayList<>();
        approveProductListAdapter=new RentRequestProductListAdapter(getActivity(),this.rentRequestArrayList);
        pendingListView.setAdapter(approveProductListAdapter);
        connectivityManagerInfo=new ConnectivityManagerInfo(getActivity());
        load_flag=false;
        offset=0;
        pendingListView.setOnScrollListener(this);
        pendingListView.setOnItemClickListener(this);




        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        load_flag=true;
        offset=0;

        if (connectivityManagerInfo.isConnectedToInternet())
            new ApprovalProductListAsyncTask(this, offset, STATE).execute();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(firstVisibleItem+visibleItemCount>=totalItemCount && !load_flag && totalItemCount!=0){
            load_flag=true;
            if (connectivityManagerInfo.isConnectedToInternet())
                new ApprovalProductListAsyncTask(this, offset, STATE).execute();

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