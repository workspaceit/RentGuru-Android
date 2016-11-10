package wsit.rentguru.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import wsit.rentguru.activity.RentDetailsActivity;
import wsit.rentguru.adapter.ApproveProductListAdapter;
import wsit.rentguru.asynctask.ApprovalDecisionAsyncTask;
import wsit.rentguru.asynctask.ApprovalProductListAsyncTask;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ConnectivityManagerInfo;


public class ApproveProductFragment extends Fragment implements ListView.OnScrollListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private View view;

    private SwipeMenuListView approveProductListView;
    public ArrayList<RentRequest> rentRequestArrayList;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int offset;
    private ApproveProductListAdapter approveProductListAdapter;
    private boolean flag_loading;
    private boolean response;
    private Button pending,approved,disapproved;
    private int state;
    private SwipeMenuCreator creator;
    private int arrPosition;
    public static final int REQUEST_CODE = 1;

    public ApproveProductFragment() {
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
        view = inflater.inflate(R.layout.fragment_approve_product, container, false);
        state = 0;
        pending = (Button)view.findViewById(R.id.pending);
        pending.setOnClickListener(this);
        pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));

        approved = (Button)view.findViewById(R.id.approved);
        approved.setOnClickListener(this);
        approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));

        disapproved = (Button)view.findViewById(R.id.disapproved);
        disapproved.setOnClickListener(this);
        disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));

        approveProductListView = (SwipeMenuListView)view.findViewById(R.id.uploaded_product_list);
        approveProductListView.setOnScrollListener(this);
        approveProductListView.setOnItemClickListener(this);

        this.rentRequestArrayList = new ArrayList<RentRequest>();
        this.connectivityManagerInfo = new ConnectivityManagerInfo(this.getContext());
        this.offset = 0;


        if(state == 0) {
                    creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem approveItem = new SwipeMenuItem(getContext().getApplicationContext());
                    // set item background
                    approveItem.setBackground(R.color.correct_color);
                    // set item width
                    approveItem.setWidth(100);
                    approveItem.setIcon(R.drawable.ic_done);


                    menu.addMenuItem(approveItem);

                    // create "delete" item
                    SwipeMenuItem disapproveItem = new SwipeMenuItem(getContext().getApplicationContext());
                    // set item background
                    disapproveItem.setBackground(R.color.delete_color);
                    // set item width
                    disapproveItem.setWidth(100);
                    // set a icon
                    disapproveItem.setIcon(R.drawable.ic_delete);
                    // add to menu
                    menu.addMenuItem(disapproveItem);
                }
            };


            approveProductListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            approveProductListView.setMenuCreator(creator);
        }



        if(connectivityManagerInfo.isConnectedToInternet())
        {
            if(state == 0)
                new ApprovalProductListAsyncTask(this,offset,0).execute();
            else if(state == 1)
                new ApprovalProductListAsyncTask(this,offset,1).execute();
            else
                new ApprovalProductListAsyncTask(this,offset,2).execute();
        }


        approveProductListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
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

                    if(state == 0)
                        new ApprovalProductListAsyncTask(this,offset,0).execute();
                    else if(state == 1)
                        new ApprovalProductListAsyncTask(this,offset,1).execute();
                    else
                        new ApprovalProductListAsyncTask(this,offset,2).execute();
                }

            }
        }


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
        this.approveProductListAdapter = new ApproveProductListAdapter(this.getContext(),this.rentRequestArrayList);
        this.approveProductListView.setAdapter(approveProductListAdapter);

        offset+=1;
        flag_loading = false;

    }

    private void getConfirmation(int type)
    {
        final int cat = type;
        new AlertDialog.Builder(this.getContext())
                .setTitle("Confirmation")
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        if (connectivityManagerInfo.isConnectedToInternet()) {
                            if(cat == 1)
                                new ApprovalDecisionAsyncTask(ApproveProductFragment.this, 1, rentRequestArrayList.get(arrPosition).getId()).execute();
                            else
                                new ApprovalDecisionAsyncTask(ApproveProductFragment.this, 2, rentRequestArrayList.get(arrPosition).getId()).execute();
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


    @Override
    public void onClick(View v) {

      

        if(v == pending)
        {
            pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));
            approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            state = 0;

            creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem approveItem = new SwipeMenuItem(getContext().getApplicationContext());
                    // set item background
                    approveItem.setBackground(R.color.correct_color);
                    // set item width
                    approveItem.setWidth(100);
                    approveItem.setIcon(R.drawable.ic_done);


                    menu.addMenuItem(approveItem);

                    // create "delete" item
                    SwipeMenuItem disapproveItem = new SwipeMenuItem(getContext().getApplicationContext());
                    // set item background
                    disapproveItem.setBackground(R.color.delete_color);
                    // set item width
                    disapproveItem.setWidth(100);
                    // set a icon
                    disapproveItem.setIcon(R.drawable.ic_delete);
                    // add to menu
                    menu.addMenuItem(disapproveItem);
                }
            };


            approveProductListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            approveProductListView.setMenuCreator(creator);

            offset = 0;
            if(connectivityManagerInfo.isConnectedToInternet())
                new ApprovalProductListAsyncTask(this,offset,0).execute();



        }
        else if( v == approved)
        {
            pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));
            disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            state = 1;
            approveProductListView.setSwipeDirection(SwipeMenuListView.GONE);
            offset = 0;
            if(connectivityManagerInfo.isConnectedToInternet())
            new ApprovalProductListAsyncTask(this,offset,1).execute();

        }
        else if(v == disapproved)
        {
            pending.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            approved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_not_selected));
            disapproved.setBackground(getResources().getDrawable(R.drawable.drawable_button_border_selected));
            state = 1;
            approveProductListView.setSwipeDirection(SwipeMenuListView.GONE);
            offset = 0;
            if(connectivityManagerInfo.isConnectedToInternet())
                new ApprovalProductListAsyncTask(this,offset,2).execute();

        }



    }

    public void onApprove() {

        this.rentRequestArrayList.remove(arrPosition);
        approveProductListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("clicked");

        Intent intent = new Intent(this.getActivity(),RentDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("arrayList",this.rentRequestArrayList);
        intent.putExtra("type", 1);
        intent.putExtra("state",state);
        //startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == this.getActivity().RESULT_OK) {
                System.out.println("ok called");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();

            }
        } catch (Exception ex) {


        }

    }


}
