package wsit.rentguru24.asynctask;

import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.CategoryProductListViewActivity;
import wsit.rentguru24.model.RentalProduct;

/**
 * Created by Tomal on 11/8/2016.
 */

public class GetRentalProductCategoryWiseAsynTask extends AsyncTask<String,String,ArrayList<RentalProduct>> {
    private CategoryProductListViewActivity categoryProductListViewActivity;
    private int categoryId,offset,limit;

    public GetRentalProductCategoryWiseAsynTask(CategoryProductListViewActivity categoryProductListViewActivity,
                                                int categoryId,int offset,int limit){
        this.categoryProductListViewActivity=categoryProductListViewActivity;
        this.categoryId=categoryId;
        this.offset=offset;
        this.limit=limit;
    }



    @Override
    protected ArrayList<RentalProduct> doInBackground(String... params) {
        return new ProductsService().getProductCategoryWise(categoryId,limit,offset);
    }

    @Override
    protected void onPostExecute(ArrayList<RentalProduct> rentalProducts) {
        super.onPostExecute(rentalProducts);
        categoryProductListViewActivity.setData(rentalProducts);
    }
}
