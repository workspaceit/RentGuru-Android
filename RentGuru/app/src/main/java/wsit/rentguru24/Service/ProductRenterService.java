package wsit.rentguru24.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import wsit.rentguru24.model.ResponseStat;

/**
 * Created by Tomal on 11/17/2016.
 */

public class ProductRenterService extends ApiManager {
    private ResponseStat responseStat;

    public boolean cancelRequest(int requestId) {

        responseStat = new ResponseStat();
        this.setController("auth/rent/cancel-request/" + requestId);

        String resp = this.getData("GET");
        System.out.println(resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (responseStat.isStatus()) {


                return true;


            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        return false;


    }


    public boolean returnProduct(int rentInfId) {

        responseStat = new ResponseStat();
        this.setController("auth/return-product/confirm-return/" + rentInfId);

        String resp = this.getData("POST");
        System.out.println(resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (responseStat.isStatus()) {


                return true;


            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        return false;


    }

}
