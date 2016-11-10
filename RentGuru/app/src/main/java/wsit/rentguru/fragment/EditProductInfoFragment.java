package wsit.rentguru.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.activity.EditProductActivity;
import wsit.rentguru.asynctask.CategoryAsncTask;
import wsit.rentguru.asynctask.GetRentalProductCategoryWiseAsynTask;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.ProductCategory;
import wsit.rentguru.utility.ConnectivityManagerInfo;


public class EditProductInfoFragment extends Fragment {
    private ArrayList<CategoryModel> categoryModels;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private int catPosition,subCatPosition;
    private Spinner catSpinner,subCatSpinner;
    private ArrayAdapter<String> catAdapter;
    private ArrayAdapter<String> subCatAdapter;
    private String[] catArr;
    private String[] subCatArr;


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
        }

        catPosition=-1;
        subCatPosition=-1;

        catSpinner=(Spinner) view.findViewById(R.id.product_category);
        subCatSpinner=(Spinner)view.findViewById(R.id.product_sub_category);

        //catAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_category,catArr);
    }


    public void setCategoryData(ArrayList<CategoryModel> categoryData){
        this.categoryModels.addAll(categoryData);
        CategoryModel pCategory=EditProductActivity.myRentalProduct.getProductCategories().get(0).getCategory();
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

            catSpinner.setSelection(catPosition);
            subCatSpinner.setVisibility(View.VISIBLE);
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

}
