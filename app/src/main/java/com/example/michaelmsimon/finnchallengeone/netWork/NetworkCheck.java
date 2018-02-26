package com.example.michaelmsimon.finnchallengeone.netWork;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;

/**
 * Created by Michael M. Simon on 2/14/2018.
 */
//checks internet connection and notify user
public class NetworkCheck {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

    }

    public static void informBadInternet(Context c){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
        }
        //TODO
        //Add options to access internet activation
        builder.setTitle("No Internet Connection!")
                .setMessage("Try connecting to Internet;)")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something here when user touches OK

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
