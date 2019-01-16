package com.example.user.myapplication.Model;

import com.example.user.myapplication.APICon.UserResource;
import com.example.user.myapplication.user.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelViewLogin {
    private static final String TAG = ModelViewLogin.class.getCanonicalName();

    public Call<Void> loginUser(String username, String password){
        User user=new User(username,password);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserResource.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        UserResource api = retrofit.create(UserResource.class);
        Call<Void> call = api.loginUser(user);
        return call;
    }
}