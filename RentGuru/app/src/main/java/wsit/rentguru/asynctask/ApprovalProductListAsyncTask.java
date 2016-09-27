package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.ApproveProductFragment;
import wsit.rentguru.activity.RentDetailsActivity;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.model.RentalProduct;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class ApprovalProductListAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentRequest>> {


    private ApproveProductFragment context;
    private int offset;
    private ArrayList<RentRequest> rentRequestArrayList;
    private ProductsService productsService;
    private int type;
    private ProgressDialog dialog;


    public ApprovalProductListAsyncTask(ApproveProductFragment context,int offset,int type)
    {
        this.context = context;
        this.offset = offset;
        this.productsService = new ProductsService();
        this.type = type;
    }

    @Override
    protected ArrayList<RentRequest> doInBackground(Boolean... params) {

        if(type == 0)
            rentRequestArrayList = productsService.getListForApproval(offset);
        else if(type == 1)
            rentRequestArrayList = productsService.getApprovedProductList(offset);
        else
            rentRequestArrayList = productsService.getDisapprovedProductList(offset);

        return rentRequestArrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context.getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();

    }



    @Override
    protected void onPostExecute(ArrayList<RentRequest> rentalProductArrayList) {
        super.onPostExecute(rentRequestArrayList);

        dialog.dismiss();

        if(rentalProductArrayList.size() > 0)
        {
            context.onDatatload(rentRequestArrayList);
        }
        else
        {
            //Toast.makeText(context.getContext(), "No Record Found", Toast.LENGTH_SHORT).show();

        }

    }


}
