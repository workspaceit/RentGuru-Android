package wsit.rentguru24.asynctask;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.fragment.ApprovedBookingFragment;
import wsit.rentguru24.fragment.DisapprovedBookingFragment;
import wsit.rentguru24.fragment.PendingBookingRequestFragment;
import wsit.rentguru24.model.RentRequest;

/**
 * Created by workspaceinfotech on 8/22/16.
 */
public class RequestedProductsListAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentRequest>> {

    private Fragment fragment;
    private int offset;
    private ArrayList<RentRequest> rentRequestArrayList;
    private ProductsService productsService;
    private int type;
    private ProgressDialog dialog;


    public RequestedProductsListAsyncTask(Fragment fragment, int offset, int type)
    {
        this.fragment = fragment;
        this.offset = offset;
        this.productsService = new ProductsService();
        this.type = type;

    }

    @Override
    protected ArrayList<RentRequest> doInBackground(Boolean... params) {

        if(type == 0)
            rentRequestArrayList = productsService.getRequestedProductsList(offset);
        else if(type == 1)
            rentRequestArrayList = productsService.getRequestedApprovedProductList(offset);
        else if (type==2)
            rentRequestArrayList = productsService.getRequestedDisapprovedProductList(offset);

        return rentRequestArrayList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(fragment.getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");

        if (offset==0) {
            dialog.show();
        }

    }



    @Override
    protected void onPostExecute(ArrayList<RentRequest> rentalProductArrayList) {
        super.onPostExecute(rentRequestArrayList);

        if (offset==0) {
            dialog.dismiss();
        }

        if(rentalProductArrayList.size() > 0)
        {

            if (fragment instanceof PendingBookingRequestFragment)
                ((PendingBookingRequestFragment)fragment).completeLoading(rentRequestArrayList);
            else if (fragment instanceof ApprovedBookingFragment)
                ((ApprovedBookingFragment)fragment).completeLoading(rentalProductArrayList);
            else if (fragment instanceof DisapprovedBookingFragment)
                ((DisapprovedBookingFragment)fragment).completeLoading(rentalProductArrayList);
        }
        else
        {
            if (fragment instanceof PendingBookingRequestFragment)
                ((PendingBookingRequestFragment)fragment).noData();
            else if (fragment instanceof ApprovedBookingFragment)
                ( (ApprovedBookingFragment)fragment).noData();
            else if (fragment instanceof DisapprovedBookingFragment)
                ((DisapprovedBookingFragment)fragment).noData();

        }

    }


}
