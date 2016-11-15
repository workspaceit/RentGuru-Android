package wsit.rentguru.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.adapter.RentRequestProductListAdapter;
import wsit.rentguru.asynctask.ApprovalProductListAsyncTask;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ConnectivityManagerInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisapprovedRentRequestFragment extends Fragment implements AbsListView.OnScrollListener{
    private View view;
    private SwipeMenuListView disapprovedListView;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private RentRequestProductListAdapter approveProductListAdapter;
    public ArrayList<RentRequest> rentRequestArrayList;
    private boolean load_flag;
    private final int STATE = 2;
    private int offset;

    public DisapprovedRentRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_disapproved_rent_request, container, false);

        disapprovedListView=(SwipeMenuListView)view.findViewById(R.id.disapprove_rent_product_list);
        this.rentRequestArrayList=new ArrayList<>();
        approveProductListAdapter=new RentRequestProductListAdapter(getActivity(),this.rentRequestArrayList);
        disapprovedListView.setAdapter(approveProductListAdapter);
        connectivityManagerInfo=new ConnectivityManagerInfo(getActivity());
        load_flag=false;
        offset=0;
        disapprovedListView.setOnScrollListener(this);

        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount>=totalItemCount && !load_flag){
            load_flag=true;
            if (connectivityManagerInfo.isConnectedToInternet())
                new ApprovalProductListAsyncTask(this, offset, STATE).execute();

        }

    }

    public void completeLoading(ArrayList<RentRequest> rentRequests){
        this.rentRequestArrayList.addAll(rentRequests);
        approveProductListAdapter.setArray(this.rentRequestArrayList);
        approveProductListAdapter.notifyDataSetChanged();
        this.offset++;
        load_flag=false;

    }
}