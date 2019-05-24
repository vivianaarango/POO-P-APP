package com.project.poop.managers;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.project.poop.R;


/**
 * Created by andres on 20/10/16.
 */
public class ManagerProgressDialog {

    private Context context;
    private ProgressDialog progress;

    public ManagerProgressDialog(Context context) {
        this.context = context;
        progress = new ProgressDialog(context);
        progress.setMessage(context.getString(R.string.loading));
        progress.setCancelable(false);
    }

    public synchronized void showProgress(){
        showProgress(null);
    }
    public synchronized void showProgress(String message){
        if (message!=null) {
            progress.setMessage(message);
        }
        try{
            progress.show();
        }catch (Exception e){
            Log.e("error dismiss ", e.getMessage());
        }
    }

    public synchronized void dismissProgress(){
        if (progress!= null && progress.isShowing() ){
            try {
                progress.dismiss();
            }catch (Exception e){
                Log.e("error dismiss ", e.getMessage());
            }
        }
    }
}
