package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.RentActivity;
import wsit.rentguru24.model.RentRequest;
import wsit.rentguru24.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/18/16.
 */
public class RequestRentAsyncTask extends AsyncTask<Boolean, Void, ResponseStat> {

    private ResponseStat responseStat;
    private ProgressDialog dialog;
    private RentActivity mcontext;
    private ProductsService productsService;
    private RentRequest rentRequest;

    public RequestRentAsyncTask(RentActivity mcontext,RentRequest rentRequest)
    {
        this.responseStat = new ResponseStat();
        this.mcontext = mcontext;
        this.rentRequest = rentRequest;
        this.productsService = new ProductsService();
    }


    @Override
    protected ResponseStat doInBackground(Boolean... params) {


        responseStat = productsService.rentProduct(rentRequest);

        return responseStat;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mcontext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Posting Request...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(ResponseStat responseStat) {
        super.onPostExecute(responseStat);
        dialog.dismiss();



        if(responseStat.isStatus()== true)
        {
            Toast.makeText(mcontext, "Request sent to the product owner", Toast.LENGTH_SHORT).show();
            mcontext.payment();
        }
        else
        {
            if(responseStat.getMsg().length()!=0)
            Toast.makeText(mcontext, responseStat.getMsg(), Toast.LENGTH_SHORT).show();
            else
            Toast.makeText(mcontext, responseStat.getRequestErrors().get(0).getMsg().toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
