package wsit.rentguru.asynctask;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.fragment.ApprovedRentRequestFragment;
import wsit.rentguru.fragment.DisapprovedRentRequestFragment;
import wsit.rentguru.fragment.PendingRentRequestFragment;
import wsit.rentguru.fragment.RentRequestFragment;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.ShowNotification;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class ApprovalProductListAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentRequest>> {


    private Fragment context;
    private int offset;
    private ArrayList<RentRequest> rentRequestArrayList;
    private ProductsService productsService;
    private int type;
    private ProgressDialog progressDialog;



    public ApprovalProductListAsyncTask(Fragment context, int offset, int type)
    {
        this.context = context;
        this.offset = offset;
        this.productsService = new ProductsService();
        this.type = type;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context.getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (offset==0)
            progressDialog.show();
    }

    @Override
    protected ArrayList<RentRequest> doInBackground(Boolean... params) {

        if(type == 0)
            rentRequestArrayList = productsService.getListForApproval(offset);
        else if(type == 1)
            rentRequestArrayList = productsService.getApprovedProductList(offset);
        else if (type==2)
            rentRequestArrayList = productsService.getDisapprovedProductList(offset);

        return rentRequestArrayList;
    }





    @Override
    protected void onPostExecute(ArrayList<RentRequest> rentalProductArrayList) {
        super.onPostExecute(rentRequestArrayList);

        if (offset==0)
            progressDialog.dismiss();

        if(rentalProductArrayList.size() > 0)
        {
            if (context instanceof PendingRentRequestFragment)
                ((PendingRentRequestFragment)context).completeLoading(rentalProductArrayList);
            else if (context instanceof ApprovedRentRequestFragment)
                ((ApprovedRentRequestFragment)context).completeLoading(rentalProductArrayList);
            else if (context instanceof DisapprovedRentRequestFragment)
                ((DisapprovedRentRequestFragment)context).completeLoading(rentalProductArrayList);

        }
        else
        {
            if (context instanceof PendingRentRequestFragment)
                ((PendingRentRequestFragment)context).noData();
            else if (context instanceof ApprovedRentRequestFragment)
                ((ApprovedRentRequestFragment)context).noData();
            else if (context instanceof DisapprovedRentRequestFragment)
                ((DisapprovedRentRequestFragment)context).noData();

        }

    }


}
