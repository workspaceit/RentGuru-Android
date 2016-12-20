package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import wsit.rentguru24.Service.PostProductService;
import wsit.rentguru24.activity.PostProductActivity;
import wsit.rentguru24.fragment.PostProductThirdFragment;
import wsit.rentguru24.utility.Utility;

/**
 * Created by workspaceinfotech on 8/16/16.
 */
public class PostProductAsyncTask extends AsyncTask<Boolean, Void, Boolean> {

    private PostProductThirdFragment context;
    private PostProductService postProductService;
    private ProgressDialog dialog;
    private Boolean response;

    public PostProductAsyncTask(PostProductThirdFragment context)
    {
        this.context = context;
        this.postProductService = new PostProductService();

    }

    @Override
    protected Boolean doInBackground(Boolean... params) {

        try {
            response = postProductService.postProduct(PostProductActivity.postProduct);
        }
        catch (Exception ex)
        {
            response = false;
        }


        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context.getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Posting...");
        dialog.show();

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        dialog.dismiss();

        if(response == true)
        {
            context.doneSubmitting();
            Utility.temporaryArrayList.clear();

        }
        else
        {
            Toast.makeText(context.getContext(), "Unable to connect to server,try again later", Toast.LENGTH_SHORT).show();
        }

    }


}
