package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.RentRequestOrderDetailsActivity;

/**
 * Created by workspaceinfotech on 8/25/16.
 */
public class CancelationAsyncTask extends AsyncTask<Boolean, Void, Boolean> {

    private RentRequestOrderDetailsActivity rentRequestOrderDetailsActivity;

    private boolean response;
    private ProductsService productsService;
    private int requestId;
    private ProgressDialog dialog;




    public CancelationAsyncTask(RentRequestOrderDetailsActivity context, int id)
    {
        this.rentRequestOrderDetailsActivity = context;
        response = false;
        this.productsService = new ProductsService();
        this.requestId = id;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {

        response = productsService.getRequestCancalation(requestId);

        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(rentRequestOrderDetailsActivity);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait...");
        dialog.show();

    }


    @Override
    protected void onPostExecute(Boolean response) {
        super.onPostExecute(response);

        dialog.dismiss();


           rentRequestOrderDetailsActivity.onCancelation(response);


    }

}
