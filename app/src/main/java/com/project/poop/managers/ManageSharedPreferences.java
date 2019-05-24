package com.project.poop.managers;

import android.content.Context;
import android.content.SharedPreferences;


public class ManageSharedPreferences {

    private final String SHARED_PREFS_FILE = "poopPreferences";
    private final String KEY_USER_ID = "userId";
    private final String KEY_EMAIL = "email";
    private final String KEY_SESSION = "nothing";
    private final String KEY_EASY = "false";
    private final String KEY_MEDIUM = "false";
    private final String KEY_HARD = "false";
    private final String KEY_EXPERT = "false";
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

    public Boolean getEasy(){
        return getSettings().getBoolean(KEY_EASY, false);
    }

    public void setEasy(Boolean easy){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(KEY_EASY, easy);
        editor.commit();
    }

    public Boolean getMedium(){
        return getSettings().getBoolean(KEY_MEDIUM, false);
    }

    public void setMedium(Boolean medium){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(KEY_MEDIUM, medium);
        editor.commit();
    }

    public Boolean getHard(){
        return getSettings().getBoolean(KEY_HARD, false);
    }

    public void setHard(Boolean hard){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(KEY_HARD, hard);
        editor.commit();
    }

    public Boolean getExpert(){
        return getSettings().getBoolean(KEY_EXPERT, false);
    }

    public void setExpert(Boolean expert){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(KEY_EXPERT, expert);
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
