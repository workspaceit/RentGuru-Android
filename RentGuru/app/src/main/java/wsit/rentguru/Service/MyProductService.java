package wsit.rentguru.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.ResponseStat;
import wsit.rentguru.utility.Utility;

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


        System.out.println(produtId+" "+path);
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

                return true;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return false;


    }

    public MyRentalProduct updateProductInfo(int productId,String name,String description,String currentValue,String rentFee,
                                             String rentTypeId,String availableFrom,String availableTill,String formattedAddress,
                                             String zip,String city,String lat,String lng,int[]categoryIds){

      this.responseStat=new ResponseStat();
        MyRentalProduct myRentalProduct=null;

        this.setController("auth/product/update-product/"+productId);
        Gson gson=new Gson();
        String categoryIdArray=gson.toJson(categoryIds);

        this.setParams("name",name);
        this.setParams("description",description);
        this.setParams("currentValue",currentValue);
        this.setParams("rentFee",rentFee);
        this.setParams("availableFrom",availableFrom);
        this.setParams("availableTill",availableTill);
        this.setParams("categoryIds",categoryIdArray);
        this.setParams("formattedAddress",formattedAddress);
        this.setParams("rentTypeId",rentTypeId);
        this.setParams("zip",zip);
        this.setParams("city",city);
        this.setParams("lat",lat);
        this.setParams("lng",lng);

        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();


            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (this.responseStat.isStatus())
            {
                myRentalProduct = gson.fromJson(jsonObject.get("responseData"), MyRentalProduct.class);
                Utility.responseStat=this.responseStat;

                return myRentalProduct;
            }
            else {

                return myRentalProduct;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return myRentalProduct;

    }

    public MyRentalProduct updateOtherImage(int productId,ArrayList<String> imageToken){
        this.responseStat=new ResponseStat();
        MyRentalProduct myRentalProduct = null;
        this.setController("auth/product/update-product/"+productId);
        Gson gson=new Gson();
        String paths=gson.toJson(imageToken);
        this.setParams("otherImagesToken",paths);
        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();


            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (this.responseStat.isStatus())
            {
                myRentalProduct = gson.fromJson(jsonObject.get("responseData"), MyRentalProduct.class);

                return myRentalProduct;
            }
            else {

                return myRentalProduct;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return myRentalProduct;
    }

    public MyRentalProduct updateProfileImage(int productID,String imageToken){
        this.responseStat=new ResponseStat();
        MyRentalProduct myRentalProduct = null;
        this.setController("auth/product/update-product/"+productID);
        this.setParams("profileImageToken",imageToken);
        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (this.responseStat.isStatus())
            {
                myRentalProduct = gson.fromJson(jsonObject.get("responseData"), MyRentalProduct.class);

                return myRentalProduct;
            }
            else {

                return myRentalProduct;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return myRentalProduct;

    }



    public boolean deleteProduct(int productId){
        this.responseStat=new ResponseStat();
        this.setController("auth/product/delete-product/"+productId);
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
