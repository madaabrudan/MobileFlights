package com.example.user.myapplication.user;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class User implements Serializable {

    @PrimaryKey
    @NonNull
    String username;
    String password;

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }


    public User(String username, String password)
    {
        this.username = username;
        this.password = password;

    }

    public String getUserId() {
        return username;
    }

    public void setUserId(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

}
