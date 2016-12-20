package wsit.rentguru24.asynctask;

import android.app.Activity;
import android.support.v4.app.Fragment;

import android.os.AsyncTask;

import java.util.ArrayList;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.SearchActivity;
import wsit.rentguru24.fragment.EditProductInfoFragment;
import wsit.rentguru24.fragment.PostProductFirstFragment;
import wsit.rentguru24.model.State;

/**
 * Created by Tomal on 11/30/2016.
 */

public class GetAllStateAsynTask extends AsyncTask<String, String, ArrayList<State>> {
    public Fragment fragment;
    public Activity activity;

    public GetAllStateAsynTask(Fragment fragment){
        this.fragment=fragment;

    }

    public GetAllStateAsynTask(Activity activity){
        this.activity=activity;
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
        }else if (activity!=null){

            ((SearchActivity)activity).stateLoadComplete(states);

        }
    }
}
