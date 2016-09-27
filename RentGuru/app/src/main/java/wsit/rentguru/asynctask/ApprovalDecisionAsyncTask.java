package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.ApproveProductFragment;
import wsit.rentguru.activity.RentDetailsActivity;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class ApprovalDecisionAsyncTask extends AsyncTask<Boolean, Void, ResponseStat> {

    private ApproveProductFragment context;
    private RentDetailsActivity rcontext;
    private int type;
    private ResponseStat response;
    private ProductsService productsService;
    private int requestId;
    ProgressDialog dialog;

    public ApprovalDecisionAsyncTask(ApproveProductFragment context, int type,int id)
    {
        this.context = context;
        this.type = type;
        response = new ResponseStat();
        this.productsService = new ProductsService();
        this.requestId = id;
    }

    public ApprovalDecisionAsyncTask(RentDetailsActivity context, int type,int id)
    {
        this.rcontext = context;
        this.type = type;
        this.requestId = id;
        response = new ResponseStat();
        this.productsService = new ProductsService();

    }


    @Override
    protected ResponseStat doInBackground(Boolean... params) {

        response = productsService.getConfirmation(type,requestId);

        return response;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(context!=null) {
            dialog = new ProgressDialog(context.getContext());
        }
        else
        {
            dialog = new ProgressDialog(rcontext);
        }

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();

    }


    @Override
    protected void onPostExecute(ResponseStat response) {
        super.onPostExecute(response);

        dialog.dismiss();

        if(response.isStatus())
        {
            if(context!=null)
            context.onApprove();
            else
            rcontext.onApprove();
        }
        else
        {
            if(response.getMsg().length()!=0)
            {
                if(context!=null)
                {
                    Toast.makeText(context.getContext(), response.getMsg(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(rcontext, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if(context!=null)
                {
                    Toast.makeText(context.getContext(), response.getRequestErrors().get(0).getMsg(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(rcontext, response.getRequestErrors().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                }

            }


        }

    }

}
