package wsit.rentguru24.Service;

import android.util.Log;




import java.io.BufferedReader;

import java.io.DataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import wsit.rentguru24.utility.Utility;

/**
 * Created by workspaceinfotech on 8/3/16.
 */
public class ApiManager {

    private URL url;
    private String response;
    private HttpURLConnection apiManager;

    private String controller;
    public String responseMsg;
    public boolean status;
    private static String sCookie;




    protected Map<String, String> getPostParams;

    public ApiManager() {
        this.controller = "";
        this.responseMsg = "";
        this.status = false;
        this.getPostParams = new HashMap<>();

    }


    public void setController(String controller) {
        this.controller = controller;
    }

    protected void setParams(Map<String, String> getPostParams) {
        this.getPostParams = getPostParams;
    }

    protected void setParams(String key, String value) {
        this.getPostParams.put(key, value);
    }


    protected String getData(String method) {


        String dataAsString = "";
        try {

            if (method.endsWith("GET")) {
                System.out.println(Utility.baseUrl+this.controller);
                URL url = new URL(Utility.baseUrl+this.controller);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if(sCookie!=null && sCookie.length()>0){
                    connection.setRequestProperty("Cookie", sCookie);
                }

                System.out.println("GET");
                connection.setRequestMethod(method);
                int responseCode = connection.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success

                    String cookie = connection.getHeaderField("set-cookie");
                    if(cookie!=null && cookie.length()>0){
                        sCookie = cookie;
                    }
                    return readStream(connection.getInputStream());
                } else {
                    System.out.println("GET request not worked");
                }
            }
            else if (method.equalsIgnoreCase("POST")) {


                Log.d("url: ",Utility.baseUrl + this.controller);
                URL obj = new URL(Utility.baseUrl+this.controller);

                apiManager = (HttpURLConnection) obj.openConnection();
                if(sCookie!=null && sCookie.length()>0){
                    apiManager.setRequestProperty("Cookie", sCookie);
                }
                apiManager.setRequestMethod(method);

                String urlParameters = getPostURLEncodedParams();

                apiManager.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(apiManager.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
                int responseCode = apiManager.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    String cookie = apiManager.getHeaderField("set-cookie");
                    if(cookie!=null && cookie.length()>0){
                        sCookie = cookie;
                    }
                    // print result
                    return readStream(apiManager.getInputStream());
                } else {
                    System.out.println("POST request not worked");
                }


            }




        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataAsString;
    }
    private static String readStream(InputStream in) throws IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));

        String nextLine = "";
        while ((nextLine = reader.readLine()) != null) {
            sb.append(nextLine);
        }
        reader.close();

        return sb.toString();
    }

    protected String getPostURLEncodedParams() {
        StringBuilder sb = new StringBuilder();
        String value = "";
        for (String key : this.getPostParams.keySet()) {
            try {
                System.out.println("key: "+key);
                value = URLEncoder.encode(this.getPostParams.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public String getCookie(){
        return sCookie;
    }


}
