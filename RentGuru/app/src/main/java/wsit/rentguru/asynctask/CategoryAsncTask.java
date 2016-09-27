package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.util.ArrayList;
import wsit.rentguru.Service.PostProductService;
import wsit.rentguru.activity.PostProductFirstFragment;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/8/16.
 */
public class CategoryAsncTask extends AsyncTask<Boolean, Void, ArrayList<CategoryModel>> {

    ProgressDialog dialog;
    PostProductFirstFragment mcontext;
    ResponseStat response;
    ArrayList<CategoryModel> categoryModelArrayList;
    PostProductService postProductService;



    public CategoryAsncTask(PostProductFirstFragment context)
    {
        this.mcontext = context;
        this.response = new ResponseStat();
        this.postProductService = new PostProductService();

    }


    @Override
    protected ArrayList<CategoryModel> doInBackground(Boolean... params) {

        this.categoryModelArrayList = postProductService.getCategory();

        return categoryModelArrayList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(ArrayList<CategoryModel> aResponse) {
        super.onPostExecute(aResponse);

        if(response !=null)
        {
            if(aResponse.size()>0)
            {
                mcontext.getCategory(aResponse);

            }

        }
        else
        {
            //Toast.makeText(mcontext, "Unable to communicate with server", Toast.LENGTH_SHORT).show();
        }

    }




}
