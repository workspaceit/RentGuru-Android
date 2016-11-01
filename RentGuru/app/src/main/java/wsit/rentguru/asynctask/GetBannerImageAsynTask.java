package wsit.rentguru.asynctask;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import wsit.rentguru.Service.ProductsService;
import wsit.rentguru.activity.HomeActivity;
import wsit.rentguru.utility.Utility;

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
