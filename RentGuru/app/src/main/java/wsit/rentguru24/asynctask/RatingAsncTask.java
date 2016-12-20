package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import wsit.rentguru24.Service.ProductDetailsService;
import wsit.rentguru24.activity.ProductDetailsActivity;

/**
 * Created by workspaceinfotech on 8/17/16.
 */
public class RatingAsncTask extends AsyncTask<Boolean, Void, Boolean> {

    private ProductDetailsActivity productDetailsActivity;
    private Float ratingValue;
    private Boolean response;
    private ProgressDialog dialog;
    private ProductDetailsService productDetailsService;
    private int productId;

    public RatingAsncTask(ProductDetailsActivity productDetailsActivity,Float ratingValue,int productId)
    {
        this.productDetailsActivity = productDetailsActivity;
        this.ratingValue = ratingValue;
        this.productId = productId;
        this.productDetailsService = new ProductDetailsService();

    }


    @Override
    protected Boolean doInBackground(Boolean... params) {

        try{

            response = this.productDetailsService.getRatingValue(ratingValue,productId);

        }catch (Exception ex)
        {
            response = false;

        }


        return response;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(productDetailsActivity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        // dialog.show();

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        // dialog.dismiss();

            productDetailsActivity.updateRating(response);



    }
}
