package wsit.rentguru.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import wsit.rentguru.R;
import wsit.rentguru.activity.HomeActivity;
import wsit.rentguru.activity.LoginActivity;
import wsit.rentguru.preference.SessionManager;

/**
 * Created by Tomal on 10/24/2016.
 */

public class ShowNotification {

    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }

    public static void showSnacksBarLong(Context context,View view,String msg){
        final Snackbar snackbar=Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
   /*     ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(context, R.color.staggered_below_color));*/
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

    }


    public static void logoutDailog(final Context context,  final SessionManager sessionManager, String title, String body){


        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("Confrim Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionManager.logoutUser();
                        Utility.authCredential=null;

                        ShowNotification.showToast(context,"You are successfully logged out");
                        Intent i=new Intent(context, LoginActivity.class);
                        context.startActivity(i);
                        if (context instanceof HomeActivity){
                            ((HomeActivity)context).finish();
                        }


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }
}
