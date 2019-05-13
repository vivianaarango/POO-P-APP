package com.project.poop.libraries;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoded {


    public String encodePass(String password){

        byte[] data = new byte[0];

        try {
            data = password.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }finally {
            String base64Encoded = Base64.encodeToString(data, Base64.NO_WRAP);
            StringBuilder sBuilder = new StringBuilder(base64Encoded);
            String passEncode = sBuilder.reverse().toString();
            return  encodeMd5(passEncode);
        }
    }


    public String encodeMd5(String passEncode)  {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(passEncode.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    public static String encodeSHA256(String password){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            String passEncode = bytesToHexString(digest.digest(password.getBytes()));
            return passEncode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // utility function
    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}

