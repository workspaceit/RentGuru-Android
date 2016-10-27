package wsit.rentguru.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.util.ArrayList;
import wsit.rentguru.Service.PostProductService;
import wsit.rentguru.activity.SearchActivity;
import wsit.rentguru.fragment.PostProductFirstFragment;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/8/16.
 */
public class CategoryAsncTask extends AsyncTask<Boolean, Void, ArrayList<CategoryModel>> {

    ProgressDialog dialog;
    PostProductFirstFragment postProductFirstFragment;
    SearchActivity searchActivity;
    ResponseStat response;
    ArrayList<CategoryModel> categoryModelArrayList;
    PostProductService postProductService;




    public CategoryAsncTask(PostProductFirstFragment context)
    {
        this.postProductFirstFragment = context;
        this.response = new ResponseStat();
        this.postProductService = new PostProductService();

    }


    public CategoryAsncTask(SearchActivity searchActivity){
        this.searchActivity=searchActivity;
        this.response=new ResponseStat();
        this.postProductService=new PostProductService();

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
                if (searchActivity==null){
                    postProductFirstFragment.getCategory(aResponse);
                }else if (postProductFirstFragment==null){
                    searchActivity.setData(aResponse);
                }


            }

        }
        else
        {
            //Toast.makeText(postProductFirstFragment, "Unable to communicate with server", Toast.LENGTH_SHORT).show();
        }

    }




}
