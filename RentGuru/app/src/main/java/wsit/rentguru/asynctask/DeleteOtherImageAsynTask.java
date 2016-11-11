package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import wsit.rentguru.Service.MyProductService;
import wsit.rentguru.fragment.EditProductImagesFragment;

/**
 * Created by Tomal on 11/11/2016.
 */

public class DeleteOtherImageAsynTask extends AsyncTask<String,String,Boolean> {
    private EditProductImagesFragment editProductImagesFragment;
    private ProgressDialog dialog;

    public DeleteOtherImageAsynTask(EditProductImagesFragment editProductImagesFragment){
        this.editProductImagesFragment=editProductImagesFragment;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(editProductImagesFragment.getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Deleting Product Image...");
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String productId=params[0];
        String path=params[1];

        return new MyProductService().deleteOtherImage(productId,path);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        dialog.dismiss();
        editProductImagesFragment.confirmDetele(aBoolean);
    }
}
