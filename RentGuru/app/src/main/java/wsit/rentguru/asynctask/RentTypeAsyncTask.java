package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.Service.PostProductService;
import wsit.rentguru.fragment.PostProductThirdFragment;
import wsit.rentguru.model.RentType;

/**
 * Created by workspaceinfotech on 8/16/16.
 */
public class RentTypeAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentType>> {

    PostProductService postProductService;
    PostProductThirdFragment context;
    ProgressDialog dialog;
    ArrayList<RentType> rentTypeArrayList;

    public RentTypeAsyncTask(PostProductThirdFragment postProductThirdFragment)
    {
        this.postProductService = new PostProductService();
        this.context = postProductThirdFragment;
    }


    @Override
    protected ArrayList<RentType> doInBackground(Boolean... params) {


        this.rentTypeArrayList = this.postProductService.getRentTypes();

        return this.rentTypeArrayList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context.getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        // dialog.show();

    }

    @Override
    protected void onPostExecute(ArrayList<RentType> aRentType) {
        super.onPostExecute(aRentType);
        // dialog.dismiss();

        if(rentTypeArrayList.size()!=0)
        {
            context.loadRentType(this.rentTypeArrayList);

        }
        else
        {
            Toast.makeText(context.getContext(), "Unable to connect to server,try again later", Toast.LENGTH_SHORT).show();
        }

    }


}
