package wsit.rentguru.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import wsit.rentguru.model.ResponseStat;

/**
 * Created by Tomal on 11/10/2016.
 */

public class MyProductService extends ApiManager {
    private ResponseStat responseStat;

    public MyProductService()
    {
        this.responseStat = new ResponseStat();

    }

    public boolean deleteOtherImage(String produtId,String path){
        this.responseStat=new ResponseStat();
        this.setController("auth/product/delete-product/other-image");
        this.setParams("productId",produtId);
        this.setParams("path",path);

        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (this.responseStat.isStatus())
            {


                return true;
            }
            else {

                return false;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return false;


    }

    public boolean deleteProduct(int productId){
        this.responseStat=new ResponseStat();
        this.setController("auth/product/delete-Product/"+productId);
        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (this.responseStat.isStatus())
            {


                return true;
            }
            else {

                return false;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return false;

    }

}
