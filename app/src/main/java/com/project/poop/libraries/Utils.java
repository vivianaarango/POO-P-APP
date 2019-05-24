package com.project.poop.libraries;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPhoneNumber (CharSequence target){
        if (!TextUtils.isEmpty(target)){

            if (target.length()==7){
                return true;
            }else if (target.length()==10){
                String numero = String.valueOf(target.charAt(0));
                if (!numero.equals("3")) {
                    return true;
                }
            }
        }
        return false;
    }

    public final static boolean isCellPhoneNumber (String typedPhonenumber){
        char firstDigit = typedPhonenumber.charAt(0);

        if (firstDigit == '3'){
            if(typedPhonenumber.length()==10){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    public final static String FormatterDate (String date)
    {
        String newDate = null;

        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm");
        try
        {
            Date form = parseFormat.parse(date);
            newDate = printFormat.format(form);

        } catch (ParseException e)
        {
            e.getMessage();
        }

        return newDate;
    }
}
