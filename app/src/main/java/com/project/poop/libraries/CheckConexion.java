package com.project.poop.libraries;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.project.poop.R;


/**
 * Created by andres on 21/02/17.
 */
public class CheckConexion {

    private Context mContext;

    public CheckConexion(Context context){
        mContext = context;
    }

    public void check(){
        if(!isConnected()){

            String texto=mContext.getResources().getString(R.string.error_conection);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setTitle(R.string.app_name);
            alertDialogBuilder.setMessage(texto);
            alertDialogBuilder.setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }
    public boolean isConnected() {

        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                }
            }
        } catch (Exception e) {
            Log.d("NetworkInfo", e.getLocalizedMessage());
            return false;
        }
        return status;
    }

    public static void check(Context context){
        if(!isConnected(context)){

            String texto=context.getResources().getString(R.string.error_conection);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(R.string.app_name);
            alertDialogBuilder.setMessage(texto);
            alertDialogBuilder.setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }

    public static boolean isConnected(Context context){

        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                }
            }
        } catch (Exception e) {
            Log.d("NetworkInfo", e.getLocalizedMessage());
            return false;
        }
        return status;
    }
}