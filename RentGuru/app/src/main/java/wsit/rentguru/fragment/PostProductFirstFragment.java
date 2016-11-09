package wsit.rentguru.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import wsit.rentguru.R;
import wsit.rentguru.activity.PostProductActivity;
import wsit.rentguru.asynctask.CategoryAsncTask;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.PostProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;


public class PostProductFirstFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{


    private Button tab1;
    private View view;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private ArrayList<CategoryModel>categoryModelArrayList;
    private Spinner productCategory;
    private Spinner productSubCategory;
    private String[] catArr;
    private String[] subCatArr;
    private ArrayAdapter<String> catAdapter;
    private ArrayAdapter<String> subCatAdapter;
    private Button from,to;
    private Calendar myCalendar;
    private int flag;
    private EditText productTitle,area,zipCode,city;
    private boolean categorySelected,subcategorySelected,formSelected,toSelected;
    private int parentCategoryPosition=0;


    private void initiate(View view)
    {
        this.tab1 = (Button)view.findViewById(R.id.tab1_next);
        this.tab1.setOnClickListener(this);

        this.connectivityManagerInfo = new ConnectivityManagerInfo(view.getContext());

        this.productCategory = (Spinner)view.findViewById(R.id.product_category);
        this.productSubCategory = (Spinner)view.findViewById(R.id.product_sub_category);

        catArr = new String[1];
        catArr[0] = "Select Category";

        catAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item_category,catArr);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(catAdapter);
        this.productCategory.setOnItemSelectedListener(this);



        subCatArr = new String[1];
        subCatArr[0] = "Select Sub-Category";
        subCatAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item_category,subCatArr);
        subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSubCategory.setAdapter(subCatAdapter);
        productSubCategory.setOnItemSelectedListener(this);

        myCalendar = Calendar.getInstance();
        this.from = (Button)view.findViewById(R.id.from);
        this.from.setOnClickListener(this);

        this.to = (Button)view.findViewById(R.id.to);
        this.to.setOnClickListener(this);


        this.productTitle = (EditText)view.findViewById(R.id.product_title);
        this.area = (EditText)view.findViewById(R.id.area);
        this.zipCode = (EditText)view.findViewById(R.id.zipCode);
        this.city = (EditText)view.findViewById(R.id.city);




    }


    public PostProductFirstFragment() {
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
        view = inflater.inflate(R.layout.fragment_first, container, false);

        initiate(view);



        if (connectivityManagerInfo.isConnectedToInternet() == true) {
            new CategoryAsncTask(this).execute();
        }


        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == tab1)
        {
            if(categorySelected == false)
            {
                Toast.makeText(getContext(), "Please Select a Category", Toast.LENGTH_SHORT).show();
            }
            else if(subcategorySelected == false)
            {
                Toast.makeText(getContext(), "Please Select a Sub Category", Toast.LENGTH_SHORT).show();

            }
            else if(this.productTitle.getText().length() == 0)
            {
                Toast.makeText(getContext(), "Product title is required", Toast.LENGTH_SHORT).show();

            }
            else if(this.formSelected == false)
            {
                Toast.makeText(getContext(), "Available date is required", Toast.LENGTH_SHORT).show();
            }
            else if(this.toSelected == false)
            {
                Toast.makeText(getContext(), "Available date is required", Toast.LENGTH_SHORT).show();
            }
            else if(this.area.getText().length() == 0)
            {
                Toast.makeText(getContext(), "Area is required", Toast.LENGTH_SHORT).show();

            }
            else if(this.zipCode.length() == 0)
            {
                Toast.makeText(getContext(), "Zip Code is required", Toast.LENGTH_SHORT).show();

            }
            else if(this.city.length() == 0)
            {
                Toast.makeText(getContext(), "City is required", Toast.LENGTH_SHORT).show();
            }
            else {

                PostProductActivity.postProduct.setName(this.productTitle.getText().toString());
                PostProductActivity.postProduct.setFormattedAddress(this.area.getText().toString());
                PostProductActivity.postProduct.setZip(this.zipCode.getText().toString());
                PostProductActivity.postProduct.setCity(this.city.getText().toString());
                PostProductActivity.viewPager.setCurrentItem(1);
            }

        }
        else if(v == from)
        {
            flag = 0;
            formSelected = true;
            new DatePickerDialog(v.getContext(), this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();


        }
        else if(v == to)
        {
            flag = 1;
            toSelected = true;
            new DatePickerDialog(v.getContext(), this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }

    }


    public void getCategory(ArrayList<CategoryModel>categoryModelArrayList)
    {
        this.categoryModelArrayList = categoryModelArrayList;


        catArr = new String[this.categoryModelArrayList.size()+1];

        catArr[0] = "Select Category";

        for(int i =0; i<this.categoryModelArrayList.size();i++)
        {

            catArr[i+1] = this.categoryModelArrayList.get(i).getName();

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item_category,catArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId())
        {
            case R.id.product_category:
                Log.d("here: ",String.valueOf(position));
                if(position!=0)
                {
                    subCatArr = new String[this.categoryModelArrayList.get(position-1).getSubcategory().size()+1];
                    subCatArr[0] = "Select Sub-Category";
                    CategoryModel[] categoryModels = new CategoryModel[categoryModelArrayList.get(position-1).getSubcategory().size()];

                    categoryModels = this.categoryModelArrayList.get(position-1).getSubcategory().toArray(categoryModels);

                    if(categoryModels.length !=0) {
                        this.productSubCategory.setVisibility(View.VISIBLE);
                        subcategorySelected = false;
                        categorySelected = true;
                        for (int i = 0; i < this.categoryModelArrayList.get(position - 1).getSubcategory().size(); i++) {
                            subCatArr[i + 1] = categoryModels[i].getName();
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item_category, subCatArr);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        productSubCategory.setAdapter(adapter);
                        this.parentCategoryPosition=position-1;
                        System.out.println(this.categoryModelArrayList.get(this.parentCategoryPosition).getName());
                    }
                    else
                    {
                        categorySelected = true;
                        subcategorySelected = true;
                        this.productSubCategory.setVisibility(View.GONE);
                        ArrayList<Integer> value = new ArrayList<Integer>();
                        value.add(this.categoryModelArrayList.get(position-1).getId());
                        System.out.println("Cat name: "+this.categoryModelArrayList.get(position-1).getName());
                        PostProductActivity.postProduct.setCategoryIds(value);


                    }
                }
                else
                {
                    categorySelected = false;
                    subcategorySelected = false;
                    this.productSubCategory.setVisibility(View.GONE);

                }
                break;


            case R.id.product_sub_category:


                if(position!=0) {

                    subcategorySelected = true;
                    ArrayList<Integer>value=new ArrayList<>();
                    value.add(this.categoryModelArrayList.get(this.parentCategoryPosition).getSubcategory().get(position-1).getId());
                    System.out.println(this.categoryModelArrayList.get(this.parentCategoryPosition).getSubcategory().get(position-1).getName());
                    PostProductActivity.postProduct.setCategoryIds(value);


                }
                else
                {
                    subcategorySelected = false;

                }

            }






    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        switch (parent.getId())
        {
            case R.id.product_category:

                categorySelected = false;

                break;


            case R.id.product_sub_category:

                if(categorySelected == true)
                subcategorySelected = false;

            break;

        }



    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if(flag == 0)
            updateLabel(from);
        else
            updateLabel(to);

    }

    private void updateLabel(Button button) {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        button.setText(sdf.format(myCalendar.getTime()));
        if(flag == 0)
        {
            PostProductActivity.postProduct.setAvailableFrom(sdf.format(myCalendar.getTime()));

        }
        else
        {
            PostProductActivity.postProduct.setAvailableTill(sdf.format(myCalendar.getTime()));
        }
    }


}
