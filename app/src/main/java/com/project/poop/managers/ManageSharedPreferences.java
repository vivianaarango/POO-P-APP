package com.project.poop.managers;

import android.content.Context;
import android.content.SharedPreferences;


public class ManageSharedPreferences {

    private final String SHARED_PREFS_FILE = "poopPreferences";
    private final String KEY_USER_ID = "userId";
    private final String KEY_EMAIL = "email";
    private final String KEY_SESSION = "nothing";
    private final String KEY_EASY = "easy";
    private final String KEY_MEDIUM = "medium";
    private final String KEY_HARD = "hard";
    private final String KEY_EXPERT = "expert";
    private final String KEY_GAME_ID = "gameid";


    private Context mContext;

    public ManageSharedPreferences(Context context){
        mContext = context;
    }
    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, mContext.MODE_PRIVATE);
    }

    public void clearAll(){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.clear();
        editor.commit();
    }


    public String getEasy(){
        return getSettings().getString(KEY_EASY, null);
    }

    public void setEasy(String easy){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_EASY, easy);
        editor.commit();
    }
    public String getMedium(){
        return getSettings().getString(KEY_MEDIUM, null);
    }

    public void setMedium(String medium){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_MEDIUM, medium);
        editor.commit();
    }

    public String getHard(){
        return getSettings().getString(KEY_HARD, null);
    }

    public void setHard(String hard){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_HARD, hard);
        editor.commit();
    }

    public String getExpert(){
        return getSettings().getString(KEY_EXPERT, null);
    }

    public void setExpert(String expert){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_EXPERT, expert);
        editor.commit();
    }

    public String getUserEmail(){
        return getSettings().getString(KEY_EMAIL, null);
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getUserId(){
        return getSettings().getString(KEY_USER_ID, null);
    }

    public void setUserId(String userId){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
    }

    public Boolean getSession(){
        return getSettings().getBoolean(KEY_SESSION, false);
    }

    public void setSession(Boolean session){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(KEY_SESSION, session);
        editor.commit();
    }

    public String getGameId(){
        return getSettings().getString(KEY_GAME_ID, null);
    }

    public void setGameId(String game_id){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_GAME_ID, game_id);
        editor.commit();
    }

}
