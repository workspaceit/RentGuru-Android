package wsit.rentguru.asynctask;

import android.content.Context;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.SearchActivity;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.utility.MakeToast;

/**
 * Created by Tomal on 10/27/2016.
 */

public class GetSearchResultAsynTask extends AsyncTask<String,String,ArrayList<RentalProduct>> {

    private Context context;
    private String query;

    public GetSearchResultAsynTask(Context context,String query){
        this.context=context;
        this.query=query;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected ArrayList<RentalProduct> doInBackground(String... strings) {

        String limit=strings[0];
        String offset=strings[1];

        query=query+"&limit="+limit+"&offset="+offset;
        return new ProductsService().getSearchProductList(query);
    }


    @Override
    protected void onPostExecute(ArrayList<RentalProduct> rentalProducts) {
        super.onPostExecute(rentalProducts);
        if (context instanceof SearchActivity){
            ((SearchActivity)context).setRentalProduct(rentalProducts);
        }

    }
}
