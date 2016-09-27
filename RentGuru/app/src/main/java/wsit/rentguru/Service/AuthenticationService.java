package wsit.rentguru.Service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import wsit.rentguru.model.AuthCredential;
import wsit.rentguru.model.IdentityType;
import wsit.rentguru.model.Login;
import wsit.rentguru.model.Registration;
import wsit.rentguru.model.ResponseStat;
import wsit.rentguru.utility.Utility;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class AuthenticationService extends ApiManager {


    private ResponseStat responseStat;
    private AuthCredential authCredential;



    public AuthenticationService()
    {
        this.responseStat = new ResponseStat();
        this.authCredential = new AuthCredential();

    }

    public ResponseStat requestLogin(Login login)
    {

        if(login.getType()==1) {
            this.setController("signin/by-email-password");
            this.setParams("email", login.getEmail());
            this.setParams("password", login.getPassword());
        }
        else
        {
            this.setController("signin/by-accesstoken");
            this.setParams("accessToken",login.getAccessToken() );

        }
        String resp = this.getData("POST");

        Log.d("resp", resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            if (this.responseStat.isStatus())
            {

                authCredential = gson.fromJson(jsonObject.get("responseData"), authCredential.getClass());
                Utility.authCredential = authCredential;

            }
            else {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return  responseStat;
    }



    public ArrayList<IdentityType> getIdentityType()
    {
        this.responseStat = new ResponseStat();
        ArrayList<IdentityType> identityTypeArrayList = new ArrayList<IdentityType>();

        this.setController("utility/get-identity");
        String resp = this.getData("GET");
        Log.d("resp",resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());
            Log.d("msq",this.responseStat.getMsg());

            if (this.responseStat.isStatus())
            {
                IdentityType[] identityType = gson.fromJson(jsonObject.get("responseData"),IdentityType[].class);
                for(IdentityType i: identityType){
                    identityTypeArrayList.add(i);
                }


            }
            else {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return identityTypeArrayList;
    }


    public String sendDocument(String mfilePath) throws IOException {
        String response = "";

        this.responseStat = new ResponseStat();
        String filenameArray[] = mfilePath.split("\\.");
        String extension = filenameArray[filenameArray.length-1];
        String attachedFilename = "test."+extension;


        String requestURL = Utility.fileUploadUrl+"document-identity";

        MultipartUtility multipart = new MultipartUtility(requestURL);
        multipart.addFilePart("documentIdentity", new File(mfilePath));
        String resp = multipart.finish(); // response from server.


        Log.d("resp", resp);

        try {

            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (responseStat.isStatus()) {


                response = jsonObject.get("responseData").getAsString();
            }

        }catch (Exception ex)
        {

            System.out.println(ex.getMessage());

        }
        return response;
    }



    public ResponseStat registrationRequest(Registration registration)
    {

        this.setController("signup/user");

        System.out.println(registration.getFirstName());
        System.out.println(registration.getLastName());
        System.out.println(registration.getEmail());
        System.out.println(registration.getPassword());
        System.out.println(registration.getIdentityType().getId());
        System.out.println(registration.getIdentityDocToken());

        this.setParams("firstName", registration.getFirstName());
        this.setParams("lastName", registration.getLastName());
        this.setParams("email", registration.getEmail());
        this.setParams("password", registration.getPassword());
        this.setParams("identityTypeId", String.valueOf(registration.getIdentityType().getId()));
        this.setParams("identityDocToken", registration.getIdentityDocToken());

        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (this.responseStat.isStatus())
            {
//                authCredential = gson.fromJson(jsonObject.get("responseData"), authCredential.getClass());
//                Utility.authCredential = authCredential;


            }
            else {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return this.responseStat;
    }


    public ResponseStat facebookRegistration(String accessToken)
    {

        this.setController("social-media/facebook/login/by-facebook-access-token");
        this.setParams("accessToken", accessToken);

        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if (this.responseStat.isStatus())
            {

                authCredential = gson.fromJson(jsonObject.get("responseData"), authCredential.getClass());
                Utility.authCredential = authCredential;

            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return responseStat;
    }



    public ResponseStat googleRegistration(String accessToken)
    {

        this.setController("social-media/google/login/by-google-access-token");
        this.setParams("accessToken", accessToken);

        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());

            if (this.responseStat.isStatus())
            {

                authCredential = gson.fromJson(jsonObject.get("responseData"), authCredential.getClass());
                Utility.authCredential = authCredential;

            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return responseStat;



    }



}
