package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.UploadedProductFragment;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/23/16.
 */
public class UploadedProductListAsyncTask extends AsyncTask<Boolean, Void, ArrayList<MyRentalProduct>> {

    private UploadedProductFragment context;
    private int offset;
    private ArrayList<MyRentalProduct> rentalProductArrayList;
    private ProductsService productsService;
    private ProgressDialog dialog;

    public UploadedProductListAsyncTask(UploadedProductFragment context,int offset)
    {
        this.context = context;
        this.offset = offset;
        this.productsService = new ProductsService();
    }


    @Override
    protected ArrayList<MyRentalProduct> doInBackground(Boolean... params) {

        rentalProductArrayList = productsService.getUploadedProductList(offset);

        return rentalProductArrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context.getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        //dialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<MyRentalProduct> rentalProductArrayList) {
        super.onPostExecute(rentalProductArrayList);
        //dialog.dismiss();



        if(rentalProductArrayList.size() > 0)
        {
            context.onDatatload(rentalProductArrayList);
        }
        else
        {
            //Toast.makeText(context.getContext(), "", Toast.LENGTH_SHORT).show();

        }

    }


}
