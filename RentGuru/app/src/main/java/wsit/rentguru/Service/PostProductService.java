package wsit.rentguru.Service;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.model.PostProduct;
import wsit.rentguru.model.RentType;
import wsit.rentguru.model.ResponseStat;
import wsit.rentguru.utility.Utility;

/**
 * Created by workspaceinfotech on 8/8/16.
 */
public class PostProductService extends ApiManager {


    private ResponseStat responseStat;

    public PostProductService()
    {
        this.responseStat = new ResponseStat();


    }

    public ArrayList<CategoryModel> getCategory()
    {

        this.responseStat = new ResponseStat();

        ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<CategoryModel>();
        this.setController("utility/get-category");

        String resp = this.getData("GET");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (this.responseStat.isStatus())
            {
               CategoryModel[] categoryModel = gson.fromJson(jsonObject.get("responseData"), CategoryModel[].class);

                for(CategoryModel i: categoryModel){
                    categoryModelArrayList.add(i);
                }

            }
            else {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }




        return categoryModelArrayList;
    }



    public String sendProductImage(String mfilePath) throws IOException {
        String response = "";

        this.responseStat = new ResponseStat();
        String filenameArray[] = mfilePath.split("\\.");
        String extension = filenameArray[filenameArray.length-1];
        String attachedFilename = "test."+extension;



        String requestURL = Utility.fileUploadUrl+"product-image";
        Log.d("url",requestURL);

        MultipartUtility multipart = new MultipartUtility(requestURL);
        multipart.addFilePart("productImage", new File(mfilePath));
        String resp = multipart.finish(); // response from server.


        Log.d("resp", resp);

        try {

            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (responseStat.isStatus()) {


                response = jsonObject.get("responseData").getAsString();
                Utility.temporaryArrayList.add(response);
            }

        }catch (Exception ex)
        {
            response = null;
            System.out.println(ex.getMessage());

        }
        return response;
    }


    public boolean postProduct(PostProduct postProduct)
    {
        boolean response = false;


        Gson g = new Gson();


        String s1 = g.toJson(postProduct.getOtherImagesToken());
        System.out.println(postProduct.getCategoryIds().size());
        String s2 = g.toJson(postProduct.getCategoryIds());
        System.out.println(s2);

        this.setController("auth/product/upload");
        this.setParams("name", postProduct.getName());
        this.setParams("description", postProduct.getDescription());
        this.setParams("profileImageToken", postProduct.getProfileImageToken());
        this.setParams("otherImagesToken", s1);
        this.setParams("currentValue", postProduct.getCurrentValue());
        this.setParams("rentFee", postProduct.getRentFee());
        this.setParams("availableFrom", postProduct.getAvailableFrom());
        this.setParams("availableTill", postProduct.getAvailableTill());
        this.setParams("categoryIds", s2);
        this.setParams("formattedAddress", postProduct.getFormattedAddress());
        this.setParams("rentTypeId", String.valueOf(postProduct.getRentType().getId()));
        this.setParams("zip", postProduct.getZip());
        this.setParams("city", postProduct.getCity());
        this.setParams("state", postProduct.getState());
       // this.setParams("lat", postProduct.getLat());
       // this.setParams("lng", postProduct.getLng());

        String resp = this.getData("POST");
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



    public ArrayList<RentType> getRentTypes()
    {
        ArrayList<RentType> rentType = new ArrayList<RentType>();

        this.setController("utility/get-rent-type");

        String resp = this.getData("GET");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if (this.responseStat.isStatus())
            {

            RentType[] rType = gson.fromJson(jsonObject.get("responseData"), RentType[].class);
            System.out.println("renttypearraysize: "+ rType.length);

                for(RentType i: rType){
                    rentType.add(i);
                }


            }
            else {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return rentType;
    }


}
