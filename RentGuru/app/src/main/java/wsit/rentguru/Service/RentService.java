package wsit.rentguru.Service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

import wsit.rentguru.model.RentInf;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.model.ResponseStat;
import wsit.rentguru.utility.Utility;

import static wsit.rentguru.utility.Utility.responseStat;

/**
 * Created by Tomal on 11/16/2016.
 */

public class RentService extends ApiManager {

    public RentInf getRentInf(int rentRequestId){


        RentInf rentInf=null;
        responseStat=new ResponseStat();
        this.setController("auth/rent-inf/get-by-rent-request-id/"+rentRequestId);

        String resp = this.getData("GET");
        System.out.println(resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (responseStat.isStatus())
            {

               rentInf = gson.fromJson(jsonObject.get("responseData"), RentInf.class);
                return rentInf;


            }

        }catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            return rentInf;
        }





    }
}
