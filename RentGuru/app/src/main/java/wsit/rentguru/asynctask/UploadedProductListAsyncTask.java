package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.fragment.UploadedProductFragment;
import wsit.rentguru.model.MyRentalProduct;

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
        dialog.setMessage("Loading my Ass...");
        //dialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<MyRentalProduct> rentalProductArrayList) {
        super.onPostExecute(rentalProductArrayList);
        dialog.dismiss();




            context.onDatatload(rentalProductArrayList);




    }


}
