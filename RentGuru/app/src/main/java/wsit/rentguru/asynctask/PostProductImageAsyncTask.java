package wsit.rentguru.asynctask;

import android.support.v4.app.Fragment;;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import wsit.rentguru.Service.PostProductService;
import wsit.rentguru.fragment.EditProductImagesFragment;
import wsit.rentguru.fragment.PostProductSecondFragment;

/**
 * Created by workspaceinfotech on 8/11/16.
 */
public class PostProductImageAsyncTask extends AsyncTask<Boolean, Void, String> {

    private Fragment fragment;
    private String response;
    private PostProductService postProductService;
    private ProgressDialog dialog;
    private ArrayList<String> filePath;

    public PostProductImageAsyncTask(Fragment context, ArrayList<String> filepath)
    {
        this.fragment = context;
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
        dialog = new ProgressDialog(fragment.getContext());
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
            if (fragment instanceof PostProductSecondFragment) {
                ((PostProductSecondFragment)fragment).nextTab(response);
            }else if (fragment instanceof EditProductImagesFragment){
                ((EditProductImagesFragment)fragment).completeImageUpload(response);
            }

        }
        else
        {
            Toast.makeText(fragment.getActivity(), "Unable to connect to server,try again later", Toast.LENGTH_SHORT).show();
        }

    }






}
