package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.SearchActivity;
import wsit.rentguru24.model.RentalProduct;

/**
 * Created by Tomal on 10/27/2016.
 */

public class GetSearchResultAsynTask extends AsyncTask<String,String,ArrayList<RentalProduct>> {

    private Context context;
    private String query;
    private ProgressDialog progressDialog;

    public GetSearchResultAsynTask(Context context,String query){
        this.context=context;
        this.query=query;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Getting Search result...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (SearchActivity.rentalSearchProducts.size()==0)
            progressDialog.show();

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
       if (progressDialog.isShowing())
           progressDialog.dismiss();

        if (context instanceof SearchActivity){
            ((SearchActivity)context).setRentalProduct(rentalProducts);
        }

    }
}