package wsit.rentguru24.asynctask;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru24.Service.PostProductService;
import wsit.rentguru24.fragment.EditProductInfoFragment;
import wsit.rentguru24.fragment.PostProductThirdFragment;
import wsit.rentguru24.model.RentType;

/**
 * Created by workspaceinfotech on 8/16/16.
 */
public class RentTypeAsyncTask extends AsyncTask<Boolean, Void, ArrayList<RentType>> {

    PostProductService postProductService;
    PostProductThirdFragment context;

    ArrayList<RentType> rentTypeArrayList;
    private EditProductInfoFragment editProductInfoFragment;

    public RentTypeAsyncTask(PostProductThirdFragment postProductThirdFragment)
    {
        this.postProductService = new PostProductService();
        this.context = postProductThirdFragment;
    }

    public RentTypeAsyncTask(EditProductInfoFragment editProductInfoFragment){
        this.editProductInfoFragment=editProductInfoFragment;
        this.postProductService = new PostProductService();
    }


    @Override
    protected ArrayList<RentType> doInBackground(Boolean... params) {


        this.rentTypeArrayList = this.postProductService.getRentTypes();

        return this.rentTypeArrayList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(ArrayList<RentType> aRentType) {
        super.onPostExecute(aRentType);
        // dialog.dismiss();

        if(rentTypeArrayList.size()!=0)
        {
            if (context!=null) {
                context.loadRentType(this.rentTypeArrayList);
            }else if (editProductInfoFragment!=null){
                    editProductInfoFragment.loadRentType(this.rentTypeArrayList);
            }
        }
        else
        {
            Toast.makeText(context.getContext(), "Unable to connect to server,try again later", Toast.LENGTH_SHORT).show();
        }

    }


}
