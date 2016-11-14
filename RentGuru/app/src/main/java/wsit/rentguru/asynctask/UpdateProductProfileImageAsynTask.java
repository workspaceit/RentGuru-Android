package wsit.rentguru.asynctask;

import android.app.Notification;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Spinner;

import wsit.rentguru.Service.MyProductService;
import wsit.rentguru.fragment.EditProductImagesFragment;
import wsit.rentguru.model.MyRentalProduct;

/**
 * Created by Tomal on 11/14/2016.
 */

public class UpdateProductProfileImageAsynTask extends AsyncTask<String,String,MyRentalProduct> {
    private EditProductImagesFragment editProductImagesFragment;
    private int productId;
    private String path;
    private ProgressDialog progressDialog;

    public UpdateProductProfileImageAsynTask(EditProductImagesFragment editProductImagesFragment,int productId,String path){
        this.editProductImagesFragment=editProductImagesFragment;
        this.productId=productId;
        this.path=path;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(editProductImagesFragment.getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
    }

    @Override
    protected MyRentalProduct doInBackground(String... params) {


        return new MyProductService().updateProfileImage(productId,path);
    }

    @Override
    protected void onPostExecute(MyRentalProduct myRentalProduct) {
        super.onPostExecute(myRentalProduct);
            progressDialog.dismiss();
            editProductImagesFragment.updateProductComplete(myRentalProduct);

    }
}
