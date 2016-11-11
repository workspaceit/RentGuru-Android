package wsit.rentguru.fragment;


import android.app.DatePickerDialog;
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
import wsit.rentguru.asynctask.GetRentalProductCategoryWiseAsynTask;
import wsit.rentguru.asynctask.RentTypeAsyncTask;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.ProductCategory;
import wsit.rentguru.model.RentType;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;


public class EditProductInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener,
        DatePickerDialog.OnDateSetListener{
    private ArrayList<CategoryModel> categoryModels;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int catPosition,subCatPosition;
    private Spinner catSpinner,subCatSpinner,spinnerRentType;
    private ArrayAdapter<String> catAdapter;
    private ArrayAdapter<String> subCatAdapter;
    private String[] catArr;
    private String[] subCatArr;
    private int categoryId,parentCategoryPosition;
    private TextView subCategoryTitleText,productTitleTextView,areaTextView,productDescriptionTextView,zipCodeTextView,cityTextView,
            currentValueTextView,rentActualFeeTextView,rentTypeSpinner;
    private Button fromButton,toButton,updateProductButton;

    private String[] rentNameArray;
    private ArrayList<RentType> rentTypeArrayList;
    private ArrayAdapter<String> rentTypeArrayAdapter;
    private Calendar myCalendar;
    private int dataFlag;
    private String fromDate,toDate;

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
        View view= inflater.inflate(R.layout.fragment_edit_product_info, container, false);

        initialize(view);
        return view;
    }

    private void initialize(View view){
        connectivityManagerInfo=new ConnectivityManagerInfo(getActivity());
        categoryModels=new ArrayList<>();
        if (connectivityManagerInfo.isConnectedToInternet()){
            new CategoryAsncTask(this).execute();
            new RentTypeAsyncTask(this).execute();
        }

        catPosition=-1;
        subCatPosition=-1;
        categoryId=-1;
        parentCategoryPosition=0;
        dataFlag=-1;

        catSpinner=(Spinner) view.findViewById(R.id.product_category);
        subCatSpinner=(Spinner)view.findViewById(R.id.product_sub_category);
        spinnerRentType=(Spinner)view.findViewById(R.id.rent_Type_Spinner);


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
        currentValueTextView.setText(Utility.CURRENCY+" "+String.valueOf(EditProductActivity.myRentalProduct.getCurrentValue()));
        rentActualFeeTextView=(TextView)view.findViewById(R.id.rent_actual_fee);
        rentActualFeeTextView.setText(Utility.CURRENCY+" "+String.valueOf(EditProductActivity.myRentalProduct.getRentFee()));
        fromButton.setOnClickListener(this);
        toButton.setOnClickListener(this);
        myCalendar=Calendar.getInstance();



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
        String date = "";
        long time = Long.valueOf(timeStamp).longValue();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);

        return cal;
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
}
