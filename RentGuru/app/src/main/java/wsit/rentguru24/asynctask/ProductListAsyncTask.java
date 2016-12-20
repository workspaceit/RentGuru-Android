package wsit.rentguru24.asynctask;

import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.HomeActivity;
import wsit.rentguru24.model.RentalProduct;
import wsit.rentguru24.utility.Utility;

/**
 * Created by workspaceinfotech on 8/10/16.
 */
public class ProductListAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentalProduct>> {

    private HomeActivity homeActivity;
    private int offset;
    private ArrayList<RentalProduct> rentalProductArrayList;
    private ProductsService productsService;

    public ProductListAsyncTask(HomeActivity homeActivity,int offset)
    {
        this.homeActivity = homeActivity;
        this.offset = offset;
        this.productsService = new ProductsService();
    }


    @Override
    protected ArrayList<RentalProduct> doInBackground(Boolean... params) {


        this.rentalProductArrayList = this.productsService.getProductList();

        return rentalProductArrayList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(ArrayList<RentalProduct> aResponse) {
        super.onPostExecute(aResponse);

        if(this.rentalProductArrayList.size() > 0)
        {

                for(int i = 0;i< rentalProductArrayList.size();i++)
                {
                    Utility.rentalProductArrayList.add(this.rentalProductArrayList.get(i));
                    HomeActivity.productsUrlList.add(Utility.picUrl + rentalProductArrayList.get(i).getProfileImage().getOriginal().getPath());

                }

                //Utility.rentalProductArrayList = this.rentalProductArrayList;
                homeActivity.generateData();


        }
        else
        {
            //Toast.makeText(postProductFirstFragment, "Unable to communicate with server", Toast.LENGTH_SHORT).show();
        }

    }



}
