package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import wsit.rentguru24.Service.MyProductService;
import wsit.rentguru24.fragment.UploadedProductFragment;

/**
 * Created by Tomal on 11/10/2016.
 */

public class DeleteProductAsynTask extends AsyncTask<String,String,Boolean> {
    private UploadedProductFragment uploadedProductFragment;
    private ProgressDialog dialog;

    public DeleteProductAsynTask(UploadedProductFragment uploadedProductFragment){
        this.uploadedProductFragment=uploadedProductFragment;
        dialog=new ProgressDialog(uploadedProductFragment.getActivity());

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String productId=params[0];
        return new MyProductService().deleteProduct(Integer.parseInt(productId));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        dialog.dismiss();
        uploadedProductFragment.deleteConfirm(aBoolean);
    }
}
