package wsit.rentguru.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import wsit.rentguru.R;
import wsit.rentguru.activity.EditProductActivity;
import wsit.rentguru.activity.PostProductActivity;
import wsit.rentguru.asynctask.CategoryAsncTask;
import wsit.rentguru.asynctask.GetAllStateAsynTask;
import wsit.rentguru.asynctask.GetRentalProductCategoryWiseAsynTask;
import wsit.rentguru.asynctask.RentTypeAsyncTask;
import wsit.rentguru.asynctask.UpdateProductInfoAsynTask;
import wsit.rentguru.asynctask.UpdateProfileInfoAsynTask;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.ProductCategory;
import wsit.rentguru.model.RentType;
import wsit.rentguru.model.State;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;

import static android.app.Activity.RESULT_OK;


public class EditProductInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener,
        DatePickerDialog.OnDateSetListener{
    private ArrayList<CategoryModel> categoryModels;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int catPosition,subCatPosition;
    private Spinner catSpinner,subCatSpinner,spinnerRentType,stateSpinner;
    private ArrayAdapter<String> catAdapter;
    private ArrayAdapter<String> subCatAdapter,stateAdapter;
    private String[] catArr;
    private String[] subCatArr;
    private int categoryId,parentCategoryPosition;
    private TextView subCategoryTitleText,productTitleTextView,areaTextView,productDescriptionTextView,zipCodeTextView,cityTextView,
            currentValueTextView,rentActualFeeTextView;
    private Button fromButton,toButton,updateProductButton;
    private String lat,lng;

    private String[] rentNameArray,stateArr;
    private ArrayList<RentType> rentTypeArrayList;
    private ArrayAdapter<String> rentTypeArrayAdapter;
    private Calendar myCalendar;
    private int dataFlag;
    private String fromDate,toDate;
    private TextView locationPickerTextView;
    private static final int PLACE_PICKER_REQUEST = 1001;
    private Place place;
    private View view;
    private int rentTypeId;
    private ArrayList<State>states;
    private int stateId;



    public EditProductInfoFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_edit_product_info, container, false);

        initialize(view);
        return view;
    }

    private void initialize(View view){
        connectivityManagerInfo=new ConnectivityManagerInfo(getActivity());
        categoryModels=new ArrayList<>();
        if (connectivityManagerInfo.isConnectedToInternet()){
            new CategoryAsncTask(this).execute();
            new RentTypeAsyncTask(this).execute();
            new GetAllStateAsynTask(this).execute();
        }

        catPosition=-1;
        subCatPosition=-1;
        categoryId=-1;
        parentCategoryPosition=0;
        dataFlag=-1;
        stateId=-1;

        catSpinner=(Spinner) view.findViewById(R.id.product_category);
        subCatSpinner=(Spinner)view.findViewById(R.id.product_sub_category);
        spinnerRentType=(Spinner)view.findViewById(R.id.rent_Type_Spinner);
        spinnerRentType.setOnItemSelectedListener(this);


        catSpinner.setOnItemSelectedListener(this);
        subCatSpinner.setOnItemSelectedListener(this);

        subCategoryTitleText=(TextView)view.findViewById(R.id.category_sub_title_text);
        subCategoryTitleText.setVisibility(View.GONE);
        productTitleTextView=(TextView)view.findViewById(R.id.product_title);
        productTitleTextView.setText(EditProductActivity.myRentalProduct.getName());
        fromButton=(Button)view.findViewById(R.id.from_button);
        toButton=(Button)view.findViewById(R.id.to_button);
        fromButton.setText(getDate(EditProductActivity.myRentalProduct.getAvailableFrom()));
        toButton.setText(getDate(EditProductActivity.myRentalProduct.getAvailableTill()));
        areaTextView=(TextView)view.findViewById(R.id.area);
        areaTextView.setText(EditProductActivity.myRentalProduct.getProductLocation().getFormattedAddress());
        productDescriptionTextView=(TextView)view.findViewById(R.id.product_description);
        productDescriptionTextView.setText(EditProductActivity.myRentalProduct.getDescription());
        zipCodeTextView=(TextView)view.findViewById(R.id.zipCode);
        zipCodeTextView.setText(EditProductActivity.myRentalProduct.getProductLocation().getZip());
        cityTextView=(TextView)view.findViewById(R.id.city);
        cityTextView.setText(EditProductActivity.myRentalProduct.getProductLocation().getCity());
        currentValueTextView=(TextView)view.findViewById(R.id.current_value);
        currentValueTextView.setText(String.valueOf(EditProductActivity.myRentalProduct.getCurrentValue()));
        rentActualFeeTextView=(TextView)view.findViewById(R.id.rent_actual_fee);
        rentActualFeeTextView.setText(String.valueOf(EditProductActivity.myRentalProduct.getRentFee()));
        fromButton.setOnClickListener(this);
        toButton.setOnClickListener(this);
        myCalendar=Calendar.getInstance();

        locationPickerTextView=(TextView)view.findViewById(R.id.location_picker_text_view);
        locationPickerTextView.setOnClickListener(this);

        updateProductButton=(Button)view.findViewById(R.id.update_product);
        updateProductButton.setOnClickListener(this);
        lat="";
        lng="";
        fromDate="";
        toDate="";

        stateSpinner=(Spinner)view.findViewById(R.id.edit_state_spinner);
        stateSpinner.setOnItemSelectedListener(this);


    }


    public void setCategoryData(ArrayList<CategoryModel> categoryData){
        this.categoryModels.addAll(categoryData);
        CategoryModel pCategory=EditProductActivity.myRentalProduct.getProductCategories().get(0).getCategory();
        catArr = new String[this.categoryModels.size()];

        for (int i=0; i<categoryModels.size(); i++){
            catArr[i]=categoryModels.get(i).getName();
        }


        catAdapter= new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_category,catArr);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);

        System.out.println(pCategory.getId()+" "+pCategory.getName()+" "+pCategory.isSubcategory());
        if (pCategory.isSubcategory()){

            for (int i=0; i<categoryModels.size(); i++){
                for (int j=0; j<categoryModels.get(i).getSubcategory().size(); j++){
                    if (pCategory.getId()==categoryModels.get(i).getSubcategory().get(j).getId()){
                        catPosition=i;
                        subCatPosition=j;
                        this.categoryId=categoryModels.get(i).getSubcategory().get(j).getId();
                        break;
                    }
                }


            }

            subCatArr=new String[categoryModels.get(catPosition).getSubcategory().size()];

            for (int i=0; i<categoryModels.get(catPosition).getSubcategory().size(); i++){
                subCatArr[i]=categoryModels.get(catPosition).getSubcategory().get(i).getName();
            }

            subCatAdapter=new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_category,subCatArr);
            subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subCatSpinner.setAdapter(subCatAdapter);

            catSpinner.setSelection(catPosition);
            subCatSpinner.setVisibility(View.VISIBLE);
            subCategoryTitleText.setVisibility(View.VISIBLE);
            subCatSpinner.setSelection(subCatPosition);

        }else {
            for (int i=0; i<this.categoryModels.size();i++){
                if (pCategory.getId()==this.categoryModels.get(i).getId()){
                    catPosition=i;
                    this.categoryId=this.categoryModels.get(i).getId();
                    break;
                }
            }
            catSpinner.setSelection(catPosition);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.product_category:
                this.categoryId=this.categoryModels.get(position).getId();
                subCatArr = new String[this.categoryModels.get(position).getSubcategory().size()];

                CategoryModel[] childCategoryModels = new CategoryModel[this.categoryModels.get(position).getSubcategory().size()];

                childCategoryModels = this.categoryModels.get(position).getSubcategory().toArray(childCategoryModels);

                if(childCategoryModels.length !=0) {

                    if (subCatSpinner.getVisibility()==View.GONE) {
                        subCatSpinner.setVisibility(View.VISIBLE);
                        subCategoryTitleText.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < this.categoryModels.get(position).getSubcategory().size(); i++) {
                        subCatArr[i] = childCategoryModels[i].getName();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item_category, subCatArr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCatSpinner.setAdapter(adapter);
                    this.parentCategoryPosition=position;
                }else {

                    if (subCatSpinner.getVisibility()==View.VISIBLE) {
                        subCatSpinner.setVisibility(View.GONE);
                        subCategoryTitleText.setVisibility(View.GONE);
                    }

                }
                break;

            case R.id.product_sub_category:
                this.categoryId=categoryModels.get(parentCategoryPosition).getSubcategory().get(position).getId();
                break;

            case R.id.rent_Type_Spinner:
                this.rentTypeId=rentTypeArrayList.get(position).getId();

                break;

            case R.id.edit_state_spinner:
                this.stateId=this.states.get(position).getId();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getDate(String timeStamp)
    {
        String date = "";
        long time = Long.valueOf(timeStamp).longValue();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        date = DateFormat.format("dd-MM-yyyy", cal).toString();

        return date;
    }

    private Calendar getCalendarDate(String timeStamp){

        long time = Long.valueOf(timeStamp).longValue();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);

        return cal;
    }

    public void loadStatesComplete(ArrayList<State> states){
        this.states=states;
        stateArr=new String[this.states.size()];

      for (int i=0; i<this.states.size(); i++){
          stateArr[i]=this.states.get(i).getName();
      }

        stateAdapter=new ArrayAdapter<String>(getContext(), R.layout.spinner_item_category, stateArr);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        for (int i=0; i<this.states.size();i++) {
            if (this.states.get(i).getId()==EditProductActivity.myRentalProduct.getProductLocation().getState().getId()){
                stateSpinner.setSelection(i);
                stateId=this.states.get(i).getId();
                break;

            }
        }

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

        for (int i=0; i<this.rentTypeArrayList.size(); i++){
            if (this.rentTypeArrayList.get(i).getId()==EditProductActivity.myRentalProduct.getRentType().getId()){
                spinnerRentType.setSelection(i);
                rentTypeId=rentTypeArrayList.get(i).getId();
                break;
            }
        }


    }



    @Override
    public void onClick(View v) {
        if (v==fromButton){
            dataFlag=1;
            Calendar date=getCalendarDate(EditProductActivity.myRentalProduct.getAvailableFrom());
            new DatePickerDialog(v.getContext(), this,date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH)).show();

        }else if (v==toButton){
            dataFlag=2;
            Calendar date=getCalendarDate(EditProductActivity.myRentalProduct.getAvailableTill());
            new DatePickerDialog(v.getContext(), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH)).show();

        }else if (v==locationPickerTextView){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }else if (v==updateProductButton){
            if (verify()){
                int []catid={categoryId};


                if (connectivityManagerInfo.isConnectedToInternet()) {
                    new UpdateProductInfoAsynTask(this, EditProductActivity.myRentalProduct.getId(), catid, productTitleTextView.getText().toString(),
                            productDescriptionTextView.getText().toString(), currentValueTextView.getText().toString(),
                            rentActualFeeTextView.getText().toString(), String.valueOf(rentTypeId), fromDate, toDate, areaTextView.getText().toString(),
                            zipCodeTextView.getText().toString(), cityTextView.getText().toString(), lat, lng,
                            String.valueOf(stateId)).execute();
                }else {
                    ShowNotification.makeToast(getActivity(),"Network Error");
                }
            }

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (dataFlag==1)
            updateLabel(fromButton);
        else if (dataFlag==2)
            updateLabel(toButton);

    }

    private void updateLabel(Button button) {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        button.setText(sdf.format(myCalendar.getTime()));
        if (dataFlag==1)
            fromDate=sdf.format(myCalendar.getTime());
        else if (dataFlag==2)
            toDate=sdf.format(myCalendar.getTime());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                this.place = PlacePicker.getPlace(data, getActivity());
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String address = String.format("%s", place.getAddress());

                stBuilder.append(placename);
                stBuilder.append("\n");

                stBuilder.append(address);

                locationPickerTextView.setText(stBuilder.toString());

                lat=String.valueOf(place.getLatLng().latitude);
                lng=String.valueOf(place.getLatLng().longitude);


            }else {

                locationPickerTextView.setText("You Didn't Pick Any Location");

            }

        }
    }


    private boolean verify(){
        if (productTitleTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"Product title is required");
            productTitleTextView.requestFocus();
            return false;
        }else if (areaTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"Area is required");
            areaTextView.requestFocus();
            return false;
        }else if (zipCodeTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"Zip Code is required");
            zipCodeTextView.requestFocus();
            return false;
        }else if (cityTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"City is required");
            cityTextView.requestFocus();
            return false;
        }else if (productDescriptionTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"Product description is required");
            productDescriptionTextView.requestFocus();
            return false;
        }else if (currentValueTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"Current value is required");
            currentValueTextView.requestFocus();
            return false;
        }else if (rentActualFeeTextView.getText().toString().equals("")){
            ShowNotification.makeToast(getActivity(),"Rent fee is required");
            rentActualFeeTextView.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public void completeProductUpdate(MyRentalProduct myRentalProduct){
        if (myRentalProduct==null){
            ShowNotification.showSnacksBarLong(getActivity(),view,Utility.responseStat.getRequestErrors().get(0).getMsg());
        }else {
            ShowNotification.showSnacksBarLong(getActivity(),view,"Product Updated Successfully");
        }
    }

}
