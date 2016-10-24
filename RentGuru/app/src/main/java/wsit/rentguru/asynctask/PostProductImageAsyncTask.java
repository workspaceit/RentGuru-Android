package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import wsit.rentguru.Service.PostProductService;
import wsit.rentguru.fragment.PostProductSecondFragment;

/**
 * Created by workspaceinfotech on 8/11/16.
 */
public class PostProductImageAsyncTask extends AsyncTask<Boolean, Void, String> {

    private PostProductSecondFragment postProductSecondFragment;
    private String response;
    private PostProductService postProductService;
    private ProgressDialog dialog;
    private ArrayList<String> filePath;

    public PostProductImageAsyncTask(PostProductSecondFragment context,ArrayList<String> filepath)
    {
        this.postProductSecondFragment = context;
        this.postProductService = new PostProductService();
        this.filePath = filepath;

    }

    @Override
    protected String doInBackground(Boolean... params) {

        for (int i = 0; i < filePath.size(); i++) {



            try {
                response = postProductService.sendProductImage(filePath.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(postProductSecondFragment.getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Uploading...");
        dialog.show();

    }

    @Override
    protected void onPostExecute(String aBoolean) {
        super.onPostExecute(aBoolean);
        dialog.dismiss();

        if(response != null)
        {
            postProductSecondFragment.nextTab(response);

        }
        else
        {
            Toast.makeText(postProductSecondFragment.getContext(), "Unable to connect to server,try again later", Toast.LENGTH_SHORT).show();
        }

    }






}
