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

    public GetSearchResultAsynTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected ArrayList<RentalProduct> doInBackground(String... strings) {
        String limit=strings[0];
        String offset=strings[1];
        String title=strings[2];
        String lat=strings[3];
        String lng=strings[4];
        String categoryId=strings[5];
        String radius=strings[6];
        System.out.println(title+" "+limit+" "+offset);


        return new ProductsService().getSearchProductList(title,lat,lng,limit,offset,categoryId,radius);
    }


    @Override
    protected void onPostExecute(ArrayList<RentalProduct> rentalProducts) {
        super.onPostExecute(rentalProducts);
        if (context instanceof SearchActivity){
            ((SearchActivity)context).setRentalProduct(rentalProducts);
        }

    }
}
