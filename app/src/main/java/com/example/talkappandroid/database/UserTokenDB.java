package com.example.talkappandroid.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.api.UserServiceAPI;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.google.gson.Gson;

public class UserTokenDB {
    private static SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static UserTokenDB instance;
    public static String SERVER_URL = "http://10.0.2.2:7201/api/";
    private static String token;

    private UserTokenDB(){
        Context context = TalkAppApplication.context;
        pref = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }

    public static UserTokenDB getInstance(){
        if(instance == null)
            instance = new UserTokenDB();
        return instance;
    }

    public static boolean checkIfExists(String token) {
        return (pref.getString(token, null) == null);
    }

    public void insertToEditor(UserLogin user, String token) {
        Gson gson = new Gson();
        editor.putString(token, gson.toJson(user)).apply();
    }

    public static UserItem getFromEditor(String token) {
        if(token != null) {
            Gson gson = new Gson();
            String json = pref.getString(token, null);
            if(json != null)
                return gson.fromJson(json, UserItem.class);
        }
        return null;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserTokenDB.token = token;
    }

}
