package wsit.rentguru24.asynctask;


import android.os.AsyncTask;

import wsit.rentguru24.Service.ProductsService;
import wsit.rentguru24.activity.HomeActivity;

/**
 * Created by Tomal on 11/1/2016.
 */

public class GetBannerImageAsynTask extends AsyncTask<String,String,Boolean> {
    private HomeActivity homeActivity;

    public GetBannerImageAsynTask(HomeActivity homeActivity){
        this.homeActivity=homeActivity;

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return new ProductsService().getBannerImages();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean){
           homeActivity.setupBannerImage();
        }
    }
}
