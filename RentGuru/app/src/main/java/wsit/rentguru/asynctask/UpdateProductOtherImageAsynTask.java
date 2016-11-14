package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru.Service.MyProductService;
import wsit.rentguru.fragment.EditProductImagesFragment;
import wsit.rentguru.model.MyRentalProduct;

/**
 * Created by Tomal on 11/14/2016.
 */

public class UpdateProductOtherImageAsynTask extends AsyncTask<String,String,MyRentalProduct> {
    private EditProductImagesFragment editProductImagesFragment;
    private int productId;
    private ArrayList<String> paths;
    private ProgressDialog  progressDialog;

    public UpdateProductOtherImageAsynTask(EditProductImagesFragment editProductImagesFragment,int productId,ArrayList<String> paths){
        this.editProductImagesFragment=editProductImagesFragment;
        this.productId=productId;
        this.paths=paths;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(editProductImagesFragment.getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Uploading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }



    @Override
    protected MyRentalProduct doInBackground(String... params) {
        return new MyProductService().updateOtherImage(productId,paths);
    }

    @Override
    protected void onPostExecute(MyRentalProduct myRentalProduct) {
        progressDialog.dismiss();
        super.onPostExecute(myRentalProduct);
        editProductImagesFragment.updateProductComplete(myRentalProduct);
    }
}
