package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.RequestedProductsListActivity;
import wsit.rentguru.model.RentRequest;

/**
 * Created by workspaceinfotech on 8/22/16.
 */
public class RequestedProductsListAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentRequest>> {

    private RequestedProductsListActivity context;
    private int offset;
    private ArrayList<RentRequest> rentRequestArrayList;
    private ProductsService productsService;
    private int type;
    private ProgressDialog dialog;


    public RequestedProductsListAsyncTask(RequestedProductsListActivity context,int offset,int type)
    {
        this.context = context;
        this.offset = offset;
        this.productsService = new ProductsService();
        this.type = type;

    }

    @Override
    protected ArrayList<RentRequest> doInBackground(Boolean... params) {

        if(type == 0)
            rentRequestArrayList = productsService.getRequestedProductsList(offset);
        else if(type == 1)
            rentRequestArrayList = productsService.getRequestedApprovedProductList(offset);
        else
            rentRequestArrayList = productsService.getRequestedDisapprovedProductList(offset);

        return rentRequestArrayList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();

    }



    @Override
    protected void onPostExecute(ArrayList<RentRequest> rentalProductArrayList) {
        super.onPostExecute(rentRequestArrayList);

        dialog.dismiss();

        if(rentalProductArrayList.size() > 0)
        {
            context.onDatatload(rentRequestArrayList);
        }
        else
        {
            //Toast.makeText(context.getContext(), "No Record Found", Toast.LENGTH_SHORT).show();

        }

    }


}
