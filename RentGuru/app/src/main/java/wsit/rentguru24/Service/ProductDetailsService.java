package wsit.rentguru24.Service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import wsit.rentguru24.model.ResponseStat;

/**
 * Created by workspaceinfotech on 8/17/16.
 */
public class ProductDetailsService extends ApiManager {

    ResponseStat responseStat;


    public ProductDetailsService()
    {
        this.responseStat = new ResponseStat();


    }


    public boolean getRatingValue(Float value,int productId)
    {
        Boolean response = false;


        this.setController("auth/product/rate-product/"+productId+"/"+value);
        String resp = this.getData("GET");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (this.responseStat.isStatus())
            {
               response = true;

            }
            else {

                response = false;

            }
        }catch (Exception e)
        {
            response = false;
            e.printStackTrace();
        }




        return response;
    }


}
