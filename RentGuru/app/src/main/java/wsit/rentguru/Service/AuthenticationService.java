package wsit.rentguru.Service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitter.sdk.android.core.models.User;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import wsit.rentguru.model.AuthCredential;
import wsit.rentguru.model.IdentityType;
import wsit.rentguru.model.Login;
import wsit.rentguru.model.Registration;
import wsit.rentguru.model.ResponseStat;
import wsit.rentguru.model.UserPaypalCredential;
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

    public boolean changeProfileInformation(String fname,String lname,String email,String oldPass,String newPass){
        this.responseStat=new ResponseStat();
        this.setController("auth/profile/edit");
        if (!oldPass.equals("")){
            this.setParams("oldPassword",oldPass);
            this.setParams("newPassword",newPass);
        }

        this.setParams("firstName",fname);
        this.setParams("lastName",lname);
        this.setParams("email",email);
        String resp=this.getData("POST");
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (this.responseStat.isStatus())
            {
                authCredential = gson.fromJson(jsonObject.get("responseData"), authCredential.getClass());
                Utility.authCredential = authCredential;
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


    public boolean setPaypalEmail(String email){
        this.responseStat=new ResponseStat();
        this.setController("auth/paypal/add-update/my-paypal-account-email");
        this.setParams("email",email);
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

    public UserPaypalCredential getPaypalEmail(){
        UserPaypalCredential userPaypalCredential=null;
        this.responseStat=new ResponseStat();
        this.setController("auth/paypal/get/my-paypal-account-email");

        String resp=this.getData("GET");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (this.responseStat.isStatus())
            {
                userPaypalCredential = gson.fromJson(jsonObject.get("responseData"), UserPaypalCredential.class);

                return userPaypalCredential;


            }
            else {
                return userPaypalCredential;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return userPaypalCredential;

    }

    public boolean changeProfilePicture(String token){
        this.responseStat=new ResponseStat();
        this.setController("auth/profile/edit");
        this.setParams("profileImageToken",token);
        String resp=this.getData("POST");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());




            if (this.responseStat.isStatus())
            {
                authCredential = gson.fromJson(jsonObject.get("responseData"), authCredential.getClass());
                Utility.authCredential = authCredential;
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

    public String uploadNewProfilePic(String mfilePath) throws IOException{
        String response = "";
        this.responseStat = new ResponseStat();

        String requestURL = Utility.fileUploadUrl+"auth/user/profile-image";
        System.out.println(requestURL);
        MultipartUtility multipart = new MultipartUtility(requestURL,getCookie());
        multipart.addFilePart("profileImage", new File(mfilePath));
        String resp = multipart.finish(); // response from server.
        System.out.println(resp);

        try {

            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"),responseStat.getClass());


            if (responseStat.isStatus()) {


                response = jsonObject.get("responseData").getAsString();
            }

        }catch (Exception ex)
        {

          ex.printStackTrace();

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
