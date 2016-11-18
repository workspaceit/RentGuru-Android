package wsit.rentguru.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class ConnectivityManagerInfo {

    private Context mContext;

    public ConnectivityManagerInfo(Context context)
    {
        this.mContext = context;

    }


    public boolean isConnectedToInternet()
    {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            return true;

        } else {

            return false;
        }


    }

    public boolean isRentGuruAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com/"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

}
