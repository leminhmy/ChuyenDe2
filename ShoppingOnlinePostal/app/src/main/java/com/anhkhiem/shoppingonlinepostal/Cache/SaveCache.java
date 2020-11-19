package com.anhkhiem.shoppingonlinepostal.Cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.anhkhiem.shoppingonlinepostal.Login.data_models.User;
import com.google.gson.Gson;

public class SaveCache {
    private static final String APP_SHARED_PREFERENCES = SaveCache.class.getCanonicalName();
    private static final String KEY_IS_LOGIN = "KEY_IS_LOGIN";
    private static final String KEY_USER = "KEY_USER";

    private static SaveCache memoryCache;
    private SharedPreferences preferences;

    private SaveCache(Context context){
        preferences = context.getSharedPreferences(APP_SHARED_PREFERENCES,Context.MODE_PRIVATE);
    }

    public synchronized static SaveCache getInstance(Context context){
        if (memoryCache == null){
            memoryCache = new SaveCache(context.getApplicationContext());
        }
        return memoryCache;
    }

    public User getUser(){
        String gSonUser =preferences.getString(KEY_USER,"");
        User user = (new Gson().fromJson(gSonUser,User.class));
        return user;
    }

    public void setUser(User user){
        String gSonUser = (new Gson().toJson(user));
        preferences.edit().putString(KEY_USER,gSonUser).apply();
    }

    public boolean isLogin() {
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setLogin(boolean isLogin) {
        preferences.edit().putBoolean(KEY_IS_LOGIN, isLogin).apply();
    }
}
