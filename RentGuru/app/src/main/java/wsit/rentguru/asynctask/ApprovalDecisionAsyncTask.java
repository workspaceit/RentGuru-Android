package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.fragment.RentRequestFragment;
import wsit.rentguru.activity.RentRequestOrderDetailsActivity;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class ApprovalDecisionAsyncTask extends AsyncTask<Boolean, Void, ResponseStat> {


    private RentRequestOrderDetailsActivity rentRequestOrderDetailsActivity;
    private ResponseStat response;
    private ProductsService productsService;
    private int requestId;
    private ProgressDialog dialog;


    public ApprovalDecisionAsyncTask(RentRequestOrderDetailsActivity context, int id) {
        this.rentRequestOrderDetailsActivity = context;

        this.requestId = id;
        response = new ResponseStat();
        this.productsService = new ProductsService();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(rentRequestOrderDetailsActivity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please Wait...");
        dialog.show();

    }


    @Override
    protected ResponseStat doInBackground(Boolean... params) {

        response = productsService.getConfirmation(requestId);

        return response;
    }


    @Override
    protected void onPostExecute(ResponseStat response) {
        super.onPostExecute(response);

        dialog.dismiss();

        rentRequestOrderDetailsActivity.onApprove(response.isStatus());


    }

}
