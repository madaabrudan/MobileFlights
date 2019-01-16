package com.example.user.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;


import com.example.user.myapplication.Model.ModelViewLogin;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText FieldUsername;
    private EditText FieldPassword;

    private ModelViewLogin viewLogin = new ModelViewLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.FieldUsername = findViewById(R.id.editText2);
        this.FieldPassword = findViewById(R.id.passwordField);

    }

    public void ButtonLogin(View view) {
        String username = FieldUsername.getText().toString();
        String password = FieldPassword.getText().toString();
        final Intent redirect = new Intent(this, FlightActivity.class);

        viewLogin.loginUser(username, password).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()&&response.code()==200) {
                    String token = response.headers().get("Authorization");
                    redirect.putExtra("token",token);
                    startActivity(redirect);
                }
                       else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setMessage("Ai introdus gresit usernameul sau parola");
                        alertDialog.show();
                    }
                }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setMessage("Nu este conectat serverul! ");
                alertDialog.show();
            }
        });
    }



}
