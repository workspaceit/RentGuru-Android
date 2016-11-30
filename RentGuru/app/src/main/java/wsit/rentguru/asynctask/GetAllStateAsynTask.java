package wsit.rentguru.asynctask;

import android.support.v4.app.Fragment;

import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.fragment.EditProductInfoFragment;
import wsit.rentguru.fragment.PostProductFirstFragment;
import wsit.rentguru.model.State;

/**
 * Created by Tomal on 11/30/2016.
 */

public class GetAllStateAsynTask extends AsyncTask<String, String, ArrayList<State>> {
    public Fragment fragment;

    public GetAllStateAsynTask(Fragment fragment){
        this.fragment=fragment;

    }

    @Override
    protected ArrayList<State> doInBackground(String... params) {
        return new ProductsService().getAllState();
    }



    @Override
    protected void onPostExecute(ArrayList<State> states) {
        super.onPostExecute(states);
        if (fragment!=null) {
            if (fragment instanceof PostProductFirstFragment)
                ((PostProductFirstFragment) fragment).stateLoadComplete(states);
            else if (fragment instanceof EditProductInfoFragment)
                ((EditProductInfoFragment)fragment).loadStatesComplete(states);
        }
    }
}
