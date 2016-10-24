package wsit.rentguru.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.activity.PostProductActivity;
import wsit.rentguru.asynctask.PostProductAsyncTask;
import wsit.rentguru.asynctask.RentTypeAsyncTask;
import wsit.rentguru.model.PostProduct;
import wsit.rentguru.model.RentType;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.Utility;


public class PostProductThirdFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private View view;
    private Spinner spinnerRentType;
    private String[] rentNameArray;
    private ArrayAdapter<String> rentTypeArrayAdapter;
    private ArrayList<RentType> rentTypeArrayList;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private EditText currentValue,rentValue;
    private Button submit;

    public PostProductThirdFragment() {
        // Required empty public constructor
    }


    private void initiate(View view)
    {
        this.rentTypeArrayList = new ArrayList<RentType>();
        this.spinnerRentType = (Spinner)view.findViewById(R.id.rent_Type_Spinner);
        this.connectivityManagerInfo = new ConnectivityManagerInfo(view.getContext());


        this.currentValue = (EditText)view.findViewById(R.id.current_value);
        this.rentValue = (EditText)view.findViewById(R.id.rent_actual_fee);
        this.submit = (Button)view.findViewById(R.id.tab3_next);
        this.submit.setOnClickListener(this);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_third, container, false);

        initiate(view);

        if(connectivityManagerInfo.isConnectedToInternet())
        {
            new RentTypeAsyncTask(this).execute();

        }

        return view;
    }


    public void loadRentType(ArrayList<RentType> rentTypeArrayList)
    {
        rentNameArray = new String[rentTypeArrayList.size()];

        this.rentTypeArrayList = rentTypeArrayList;

        for(int i=0;i<rentTypeArrayList.size();i++)
        {
            rentNameArray[i] = rentTypeArrayList.get(i).getName();

        }


        rentTypeArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_category, rentNameArray);
        rentTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRentType.setAdapter(rentTypeArrayAdapter);
        this.spinnerRentType.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId())
        {
            case R.id.rent_Type_Spinner:
                Log.d("here: ", String.valueOf(position));
                PostProductActivity.postProduct.setRentType(rentTypeArrayList.get(position));
                break;


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        PostProductActivity.postProduct.setRentType(rentTypeArrayList.get(0));

    }

    @Override
    public void onClick(View v) {

        if(v == submit)
        {
            if(currentValue.getText().length() == 0)
            {
                Toast.makeText(getContext(),"Enter Current Value",Toast.LENGTH_SHORT).show();
                return;

            }
            else if(rentValue.getText().length() == 0)
            {
                Toast.makeText(getContext(),"Enter Rent Value",Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                String mainPic = "";
                if(Utility.temporaryArrayList.size() == 1)
                {
                    mainPic = Utility.temporaryArrayList.get(0);
                }
                else
                {
                   mainPic = Utility.temporaryArrayList.get(0);

                    Utility.temporaryArrayList.remove(0);

                }

                PostProductActivity.postProduct.setProfileImageToken(mainPic);
                PostProductActivity.postProduct.setOtherImagesToken(Utility.temporaryArrayList);
                PostProductActivity.postProduct.setCurrentValue(this.currentValue.getText().toString());
                PostProductActivity.postProduct.setRentFee(this.rentValue.getText().toString());

                if(connectivityManagerInfo.isConnectedToInternet())
                new PostProductAsyncTask(this).execute();
            }


        }


    }


    public void doneSubmitting()
    {
        Toast.makeText(getContext(),"Wait for approval",Toast.LENGTH_SHORT).show();
        getActivity().finish();

    }
}
