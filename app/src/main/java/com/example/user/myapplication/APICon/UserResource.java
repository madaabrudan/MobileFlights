package com.example.user.myapplication.APICon;

import com.example.user.myapplication.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserResource {
    String IP = "192.168.0.101:8080";
    String BASE_URL = "http://" + IP ;
    @POST("/api/v1/users/addUser")
    Call<User> addUser(@Body User user);
    @POST("/login")
    Call<Void> loginUser(@Body User user);
}
