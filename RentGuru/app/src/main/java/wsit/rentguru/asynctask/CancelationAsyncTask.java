package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.RentRequestOrderDetailsActivity;
import wsit.rentguru.activity.RequestedProductsListActivity;

/**
 * Created by workspaceinfotech on 8/25/16.
 */
public class CancelationAsyncTask extends AsyncTask<Boolean, Void, Boolean> {

    private RequestedProductsListActivity context;
    private int type;
    private boolean response;
    private ProductsService productsService;
    private int requestId;
    private ProgressDialog dialog;
    private RentRequestOrderDetailsActivity rentDetailsActivity;

    public CancelationAsyncTask(RequestedProductsListActivity context, int id)
    {
        this.context = context;
        this.type = type;
        response = false;
        this.productsService = new ProductsService();
        this.requestId = id;


    }

    public CancelationAsyncTask(RentRequestOrderDetailsActivity context, int id)
    {
        this.rentDetailsActivity = context;
        this.type = type;
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
        if(context!=null)
            dialog = new ProgressDialog(context);
        else
            dialog = new ProgressDialog(rentDetailsActivity);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();

    }


    @Override
    protected void onPostExecute(Boolean response) {
        super.onPostExecute(response);

        dialog.dismiss();

        if(response)
        {
            if(context!=null)
            context.onCancelation();
            else
            rentDetailsActivity.onCancelation();
        }
        else
        {
            Toast.makeText(context, "Couldn't perform,try again later", Toast.LENGTH_SHORT).show();

        }

    }

}
