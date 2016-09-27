package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.RentActivity;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 9/26/16.
 */
public class PaymentAsyncTask extends AsyncTask<Boolean, Void, ResponseStat> {

    private ResponseStat responseStat;
    private ProductsService productsService;
    private String transactionId;
    private ProgressDialog dialog;
    private RentActivity context;
    private int rentRequestId;

    public PaymentAsyncTask(String transactionId,RentActivity context,int rentRequestId)
    {
        this.productsService = new ProductsService();
        this.responseStat = new ResponseStat();
        this.transactionId = transactionId;
        this.context = context;
        this.rentRequestId = rentRequestId;
    }



    @Override
    protected ResponseStat doInBackground(Boolean... params) {

        responseStat = productsService.getPaypalPaymentResponse(transactionId,rentRequestId);
        return responseStat;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Posting...");
        dialog.show();

    }

    @Override
    protected void onPostExecute(ResponseStat responseStat) {
        super.onPostExecute(responseStat);
        dialog.dismiss();

        if(responseStat.isStatus() == false) {
            if (responseStat.getMsg().length() != 0)
                Toast.makeText(context, responseStat.getMsg().toString(), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, responseStat.getRequestErrors().get(0).getMsg(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            context.finishActivity();
        }
    }

}
